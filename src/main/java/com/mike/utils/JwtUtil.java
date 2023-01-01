package com.mike.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: 23236
 * @createTime: 2022/12/29   14:31
 * @description: token生成管理工具
 **/

@Component
public class JwtUtil {
    // Token过期时间30分钟
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static boolean verify(String token, String username, String password) {
        try {
            // 设置加密算法
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);

    }


    public static String getUserNameByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if ("token".equals(cookies[i].getName())) {
                    token =cookies[i].getValue();
                }
            }
        }
        if (token==null)
            return null;
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username")
                .asString();
    }


    public boolean validateToken(String authHeader, UserDetails userDetails) {
        return true;
    }

    public String getUsernameFromToken(String authHeader) {
        DecodedJWT jwt = JWT.decode(authHeader);
        return jwt.getClaim("username")
                .asString();
    }

    public String getHeader() {
        return "token";
    }

    public String generateToken(UserDetails userDetails) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(userDetails.getPassword());
        // 附带username信息
        return JWT.create()
                .withClaim("username", userDetails.getUsername())
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public Claims verifyJwt(String authToken) {
        //签名秘钥，和生成的签名的秘钥一模一样

        Claims claims;
        try {
            //得到DefaultJwtParser
            claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey("secret")
                    .parseClaimsJws(authToken).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }//设置需要解析的jwt
        return claims;
    }

}
