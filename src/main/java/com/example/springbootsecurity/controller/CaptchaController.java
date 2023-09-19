package com.example.springbootsecurity.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@Slf4j
public class CaptchaController {
    /**
     * 获取图片验证码
     */
    @GetMapping("/code/image")
    public void getCaptchaCode(HttpServletRequest httpRequest,HttpServletResponse httpResponse) {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        String code = circleCaptcha.getCode();
        log.info("生成的图片验证码:{}",code);
        //将图片验证码存在session中
        httpRequest.getSession().setAttribute("CAPTCHA_CODE",code);

        try {
            //将图片写到响应流中,流的复制
            circleCaptcha.write(httpResponse.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
