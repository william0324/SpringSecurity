package com.example.springbootsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
@Slf4j
public class IndexController {
    @RequestMapping("/toIndex")
    public String toIndex() {
        return "main";
    }
}
