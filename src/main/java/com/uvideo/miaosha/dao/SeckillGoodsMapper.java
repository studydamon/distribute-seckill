package com.uvideo.miaosha.dao;

import com.uvideo.miaosha.vo.SeckillGoods;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeckillGoodsMapper extends JpaRepository<SeckillGoods,Long> {

    /**
     * 根据秒杀商品id获取秒杀商品信息
     * @param goodsId 秒杀商品id
     * @return
     */
    public SeckillGoods getByGoodsId(@Param("goodsId") Long goodsId);

}
