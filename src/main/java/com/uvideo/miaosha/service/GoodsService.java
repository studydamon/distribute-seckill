package com.uvideo.miaosha.service;

import com.uvideo.miaosha.dao.GoodsMapper;
import com.uvideo.miaosha.vo.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<Goods> getGoods(){
        List<Goods> goods = goodsMapper.findAll();
        if(CollectionUtils.isEmpty(goods)){
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
