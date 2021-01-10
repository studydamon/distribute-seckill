package com.uvideo.miaosha.controller;

import com.uvideo.miaosha.context.limiter.RateLimiter;
import com.uvideo.miaosha.dao.SeckillGoodsMapper;
import com.uvideo.miaosha.service.GoodsService;
import com.uvideo.miaosha.service.SeckillGoodsService;
import com.uvideo.miaosha.service.SeckillService;
import com.uvideo.miaosha.vo.Goods;
import com.uvideo.miaosha.vo.ResponseVo;
import com.uvideo.miaosha.vo.SeckillGoods;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SeckillController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Autowired
    private SeckillService seckillService;

    @GetMapping(value = "/fetchActivities")
    public List<SeckillGoods> getSeckillGoodsList() {
        return seckillGoodsService.fetchActivities();
    }

    @RateLimiter(count = 1)
    @PostMapping(value = "/seckill")
    public ResponseVo seckill(@RequestParam("goodId") Long goodId) {
        if (seckillService.doSeckill(goodId)) {
            return new ResponseVo().success();
        }
        System.out.println("end");
        return new ResponseVo<String>().success("aaa");
    }
}
