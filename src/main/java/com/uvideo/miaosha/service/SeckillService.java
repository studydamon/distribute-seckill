package com.uvideo.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.uvideo.miaosha.context.queue.KafkaQueueContext;
import com.uvideo.miaosha.dao.SeckillGoodsMapper;
import com.uvideo.miaosha.service.component.RedisService;
import com.uvideo.miaosha.vo.Order;
import com.uvideo.miaosha.vo.SeckillGoods;
import com.uvideo.miaosha.vo.SeckillStatus;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Import(KafkaQueueContext.class)
public class SeckillService {

    @Value("${kafka.topic.name}")
    private String kafkaTopicName;
    @Value("${redis.key.prefix}")
    private String redisKeyPrefix;

    @Autowired
    private RedisService redisService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    public SeckillStatus doSeckill(@Param("goodId") Long goodId) {
        SeckillGoods seckillGoods =
                JSON.parseObject(redisService.get(redisKeyPrefix + goodId), SeckillGoods.class);
        int stock = seckillGoods.getGoodsStock();
        if (--stock < 0) {
            return SeckillStatus.SECKILL_ERROR;
        }
        // 修改库存 - 分布式锁 - 缓存
        try {
            if ("0".equals(redisService.getAndSet("ds:lock", "1"))) {
                seckillGoods.setGoodsStock(stock);
            }
        } finally {
            redisService.set("ds:lock", "0");
        }

        // 增加订单-放入到异步订单中
        Order order = new Order();
        order.setGoodsId(seckillGoods.getId());
        order.setGoodsCount(1);
        try {
            kafkaTemplate.send(new ProducerRecord<>(kafkaTopicName, JSON.toJSONString(order)));
        } catch (Exception e) {
            log.error("kafka订单设置异常");
        }
        return SeckillStatus.SECKILL_SUCCESS;
    }

    private String keys(long goodsId) {
        return "activities:good:detail:" + goodsId;
    }

}
