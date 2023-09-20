package com.example.springbootsecurity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    @Value("${my.secretKey}")
    private String secret;

    /**
     * 创建Token
     * @param userInfo 用户信息，包含用户姓名，用户id
     * @param authList 用户权限列表
     * @return Token字符串
     */
    public String createJwt(String userInfo, List<String> authList) {
        //头部信息
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg","HS256");
        headerClaims.put("typ","JWT");
        //签发时间
        Date date = new Date();
        //过期时间 30分钟
        Date expireDate = new Date(date.getTime()+1000*60*5);
        String sign = JWT.create()
                //头部
                .withHeader(headerClaims)
                //载荷
                .withIssuer("romaneekang")
                .withIssuedAt(date)
                .withExpiresAt(expireDate)
                .withClaim("userInfo", userInfo)
                .withClaim("authList", authList)
                //签名
                .sign(Algorithm.HMAC256(secret));
        return sign;
    }

    /**
     * 校验Token
     * @param jwtToken 传入携带Token
     * @return true: 合法 false：非法
     */
    public boolean verifyToken(String jwtToken) {
        //获取验签类对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            //验签，如果不报错，则说明jwt是合法的，而且也没有过期
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            log.info("token验证正确");
            return true;
        } catch (Exception e) {
            log.info("token验证不正确");
            return false;
        }
    }

    /**
     * 获取用户信息
     * @param jwtToken 待被解析的Token字符串
     * @return 用户信息JSON字符串
     */
    public String getUserInfoFromToken(String jwtToken) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            return decodedJWT.getClaim("userInfo").asString();
        } catch (Exception e) {
            log.info("token验证不正确");
            return null;
        }
    }

    /**
     * 获取用户权限
     * @param jwtToken 待被解析的Token字符串
     * @return 用户权限列表
     */
    public List<String> getUserAuthListFromToken(String jwtToken) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            return decodedJWT.getClaim("authList").asList(String.class);
        } catch (Exception e) {
            log.info("token验证不正确");
            return null;
        }
    }
}
