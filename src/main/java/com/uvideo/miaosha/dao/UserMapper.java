package com.uvideo.miaosha.dao;

import com.uvideo.miaosha.vo.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<User,Long> {

    /**
     * 根据唯一标识获取用户实体
     * @param uniqueId 手机号唯一标识
     * @return
     */
    public User findUserByUniqueId(@Param("uniqueId") String uniqueId);
}
