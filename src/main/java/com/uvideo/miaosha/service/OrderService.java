package com.uvideo.miaosha.service;

import com.uvideo.miaosha.dao.OrderMapper;
import com.uvideo.miaosha.vo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public boolean saveOrder(Order order){
        Order save = orderMapper.save(order);
        return save == null;
    }
}
