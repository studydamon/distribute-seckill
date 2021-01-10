package com.uvideo.miaosha.context.initialize;

import com.alibaba.fastjson.JSONObject;
import com.uvideo.miaosha.dao.GoodsMapper;
import com.uvideo.miaosha.dao.SeckillGoodsMapper;
import com.uvideo.miaosha.service.SeckillGoodsService;
import com.uvideo.miaosha.service.component.RedisService;
import com.uvideo.miaosha.vo.Goods;
import com.uvideo.miaosha.vo.SeckillGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 缓存预热
 */
@Slf4j
@Component
public class WarmUpCacheInitializer implements InitializingBean {

    @Resource
    private RedisService redisService;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SeckillGoods> activities = seckillGoodsMapper.findAll();
        if (!CollectionUtils.isEmpty(activities)) {
            List<SeckillGoods> collect =
                    activities.stream().filter(new Predicate<SeckillGoods>() {
                        @Override
                        public boolean test(SeckillGoods g) {
                            return isGoing(g.getStartTime(), g.getEndTime());
                        }
                    }).collect(Collectors.toList());
            activities.forEach(activity -> {
                String key = keys(activity.getGoods().getId());
                redisService.lpush("activities", key);
                redisService.set(
                        key, JSONObject.toJSONString(activity).replace("\\", ""), expire(activity));
            });
        }
    }

    private long expire(SeckillGoods activity) {
        long rangeTime = activity.getEndTime().getTime() - activity.getStartTime().getTime();
        return rangeTime / 1000 + (long) Math.random() * 1000;
    }

    private String keys(long goodsId) {
        return "activities:good:detail:" + goodsId;
    }

    private boolean isGoing(Date start, Date end) {
        if (start == null || end == null) {
            return false;
        }
        if (start.getTime() >= end.getTime()) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long startTime = start.getTime();
        long endTime = end.getTime();
        log.info(currentTime + ":" + startTime + ":" + endTime);
        return currentTime > startTime && currentTime < endTime;
    }
}
