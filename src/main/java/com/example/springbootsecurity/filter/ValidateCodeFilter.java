package com.example.springbootsecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Component
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //判断路径是否是/login/doLogin
        if(!requestURI.equals("/login/doLogin")) {
            //直接放行
            doFilter(request,response,filterChain);
            return;
        }
        //1.从前端获取验证码
        String code = request.getParameter("code");
        //2.从session中获取验证码
        String captchaCode = (String) request.getSession().getAttribute("CAPTCHA_CODE");
        //3.判断二者是否相等
        //清除提示信息
        request.getSession().removeAttribute("captchaCodeErrorMsg");
        if (StringUtils.isEmpty(code)) {
            request.getSession().setAttribute("captchaCodeErrorMsg","请输入验证码!");
            response.sendRedirect("/login/toLogin");
            return;
        }
        if (code.equalsIgnoreCase(captchaCode)) {
            //直接放行
            doFilter(request,response,filterChain);
        } else {
            request.getSession().setAttribute("captchaCodeErrorMsg","验证码输入错误!");
            response.sendRedirect("/login/toLogin");
        }
    }
}
