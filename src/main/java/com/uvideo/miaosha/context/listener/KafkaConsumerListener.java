package com.uvideo.miaosha.context.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uvideo.miaosha.dao.OrderMapper;
import com.uvideo.miaosha.service.OrderService;
import com.uvideo.miaosha.vo.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.SQLDataException;
import java.sql.SQLException;


@Slf4j
@Component
public class KafkaConsumerListener {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = {"topic_order"}, groupId = "consumer-group-seckill")
    public void listener(ConsumerRecord<?, ?> record) {
        log.info("kafka消费者,topic[{}],partition:[{}],offset:[{}],value:[{}]",
                record.topic(), record.partition(), record.offset(), record.value());
        Order order = JSONObject.parseObject((String) record.value(), Order.class);
        try {
            boolean status = orderService.saveOrder(order);
            if (status) {
                log.info("抢购成功,请确认订单->邮件或短信提醒");
            }
        } catch (Exception ex) {
            log.error("很遗憾，您未能抢购到：{}", record.value());
        }
    }
}
