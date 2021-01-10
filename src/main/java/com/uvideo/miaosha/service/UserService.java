package com.uvideo.miaosha.service;

import com.uvideo.miaosha.dao.UserMapper;
import com.uvideo.miaosha.vo.User;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserByUinqueId(@Param("uniqueId") String uniqueId){
        User user = userMapper.findUserByUniqueId(uniqueId);
        return Objects.nonNull(user) ? user : null;
    }

    public boolean saveUser(User user){
        User save = userMapper.save(user);
        return save == null;
    }

}
