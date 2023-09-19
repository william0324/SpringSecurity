package com.example.springbootsecurity.config;

import com.example.springbootsecurity.filter.ValidateCodeFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Resource
    private ValidateCodeFilter validateCodeFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //在认证过滤器之前，添加图片验证码过滤器
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/code/image").permitAll()    //放开验证码的请求
                    .anyRequest().authenticated()    //任何请求,都需要认证登录,没有配置的只要登录就可以
            )
            .logout(logout->logout.logoutSuccessUrl("/login/toLogin"))   //退出成功
            .formLogin(
                form->form
                        .permitAll()
                        .loginPage("/login/toLogin")
                        .usernameParameter("uname") //用户参数
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login/doLogin")   //点击登陆后进入url,不是我们提供的，它相当于告诉框架request.usernameParameter
                        .failureForwardUrl("/login/toLogin")
                        .successForwardUrl("/index/toIndex")
            );
        http.csrf(AbstractHttpConfigurer::disable);//关闭跨域请求保护

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
