package com.lany.controller;

import com.lany.authorization.manager.TokenManager;
import com.lany.authorization.model.TokenModel;
import com.lany.config.ResultStatus;
import com.lany.domain.User;
import com.lany.model.ResponseResult;
import com.lany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/login")
    public ResponseResult login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        User user = userRepository.findByUsername(username);
        if (user == null ||  //未注册
                !user.getPassword().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return ResponseResult.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(user.getId());
        return ResponseResult.ok(model);
    }

    @RequestMapping(value = "/logout")
    public ResponseResult logout(@RequestParam String token) {
        tokenManager.deleteToken(token);
        return new ResponseResult(ResultStatus.SUCCESS, "退出登录成功");
    }

}
