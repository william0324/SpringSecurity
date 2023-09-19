package com.example.springbootsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @RequestMapping("/toLogin")
    public String toLogin() {
       // /templates/+login+.html
        return "login";
    }
}
