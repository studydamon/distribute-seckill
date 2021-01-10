package com.uvideo.miaosha.controller;

import com.uvideo.miaosha.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;



}
