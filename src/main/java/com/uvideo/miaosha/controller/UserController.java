package com.uvideo.miaosha.controller;

import com.uvideo.miaosha.dao.UserMapper;
import com.uvideo.miaosha.service.UserService;
import com.uvideo.miaosha.vo.ResponseVo;
import com.uvideo.miaosha.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseVo login(User user) {
        if (checkUserParam(user)) {
            return new ResponseVo<User>().error(user);
        }
        try {
            User uniqueId = userService.getUserByUinqueId(user.getUniqueId());
            if (uniqueId == null) {
                return new ResponseVo<User>().error(user);
            }
            if (uniqueId.getPassword().equals(user.getPassword())) {
                return new ResponseVo<User>().error(user);
            }
        } catch (Exception ex) {
            return new ResponseVo<User>().error(user);
        }
        return new ResponseVo<User>().success(user);
    }

    @PostMapping(value = "/register")
    public ResponseVo register(User user) {
        if (checkUserParam(user)) {
            return new ResponseVo<User>().error(user);
        }
        try {
            boolean status = userService.saveUser(user);
            if (!status) {
                return new ResponseVo<User>().error(user);
            }
        } catch (Exception ex) {
            return new ResponseVo<User>().error(user);
        }
        return new ResponseVo<User>().success(user);
    }

    private boolean checkUserParam(User user) {
        if (user == null) {
            return false;
        }
        if (user.getUniqueId() == "" || user.getUniqueId() == null) {
            return false;
        }
        if (user.getPassword() == "" || user.getPassword() == null) {
            return false;
        }
        return true;
    }
}
