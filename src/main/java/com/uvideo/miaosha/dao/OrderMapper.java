package com.uvideo.miaosha.dao;

import com.uvideo.miaosha.vo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends JpaRepository<Order,Long> {

}
