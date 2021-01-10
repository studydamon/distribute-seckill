package com.uvideo.miaosha.service;

import com.alibaba.fastjson.JSON;
import com.uvideo.miaosha.dao.SeckillGoodsMapper;
import com.uvideo.miaosha.service.component.RedisService;
import com.uvideo.miaosha.vo.SeckillGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SeckillGoodsService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    public List<SeckillGoods> fetchActivities() {
        List<String> activityKeys = redisService.keysOfList("activities");
        if (CollectionUtils.isEmpty(activityKeys)) {
            return new ArrayList<>();
        }
        List<SeckillGoods> activities = new ArrayList<>();
        activityKeys.stream().forEach(key->{
            activities.add(JSON.parseObject(redisService.get(key), SeckillGoods.class));
        });
        return activities;
    }
}
