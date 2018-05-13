package com.lany.controller;

import com.lany.authorization.annotation.Authorization;
import com.lany.authorization.annotation.CurrentUser;
import com.lany.domain.User;
import com.lany.model.ResponseResult;
import com.lany.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * http://127.0.0.1:8080/user/detail?id=1&token=3ad5f6ab4c7b48a19b396cac93979e95
     */
    @Authorization
    @RequestMapping("/detail")
    public ResponseResult getUserInfoById(@CurrentUser User currentUser, @RequestParam long id) {
        log.info("当前请求的用户：" + currentUser.toString());
        User user = userRepository.findById(id).get();
        return ResponseResult.ok(user);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
