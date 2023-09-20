package com.example.springbootsecurity.handle;

import com.example.springbootsecurity.pojo.User;
import com.example.springbootsecurity.util.JwtUtil;
import com.example.springbootsecurity.vo.HttpResult;
import com.example.springbootsecurity.vo.SecurityUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 认证成功处理器
 */
@Component
@Slf4j
public class MyAutheticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;    //注入redis
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取用户信息
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        //TODO
        //将用户信息转换成JSON字符串
        String userInfo = objectMapper.writeValueAsString(user);
        //获取权限信息
        List<SimpleGrantedAuthority> authorityList = (List<SimpleGrantedAuthority>) userDetails.getAuthorities();
        List<String> authList = authorityList.stream().map(SimpleGrantedAuthority::getAuthority).toList();
        //生成jwt
        String jwt = jwtUtil.createJwt(userInfo, authList);
        //将生成的jwt放入到redis中，设置过期时间和jwt的过期时间
        stringRedisTemplate.opsForValue().set("loginToken:"+jwt, objectMapper.writeValueAsString(authentication),5, TimeUnit.MINUTES);
        //响应给前端

        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(HttpResult.ok("ok",jwt));
        writer.flush();
    }
}
