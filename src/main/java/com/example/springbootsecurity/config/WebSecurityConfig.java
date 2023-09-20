package com.example.springbootsecurity.config;

import com.example.springbootsecurity.filter.JwtCheckFilter;
import com.example.springbootsecurity.handle.MyAutheticationSuccessHandler;
import com.example.springbootsecurity.handle.MyLogoutSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private MyAutheticationSuccessHandler myAutheticationSuccessHandler;    //注入认证成功处理器
    @Resource
    private JwtCheckFilter jwtCheckFilter;  //过滤器
    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;  //注入退出成功处理器
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)    //Token过滤器，在用户名密码认证之前校验token
                .authorizeHttpRequests((auth) -> auth
                    .anyRequest().authenticated()    //任何请求,都需要认证登录,没有配置的只要登录就可以
            )
            .logout(logout->logout.logoutSuccessHandler(myLogoutSuccessHandler))   //退出成功
            .formLogin(
                form->form
                        .permitAll()
                        .loginPage("/login/toLogin")
                        .usernameParameter("uname") //用户参数
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login/doLogin") ////点击登陆后进入url,不是我们提供的，它相当于告诉框架request.usernameParameter
                        .successHandler(myAutheticationSuccessHandler)
            );
        http.csrf(AbstractHttpConfigurer::disable);//关闭跨域请求保护
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
