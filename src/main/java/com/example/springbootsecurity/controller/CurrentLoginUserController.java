package com.example.springbootsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CurrentLoginUserController {
    //获取用户认证信息
    @GetMapping("/getLoginUser1")
    public Authentication getLoginUser1(Authentication authentication) {
        return authentication;
    }
}
