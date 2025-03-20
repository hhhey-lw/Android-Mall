package com.wei.diploma_project.util;

import com.wei.diploma_project.bean.UserBean;
import io.jsonwebtoken.*;

import java.util.Date;

/*
* JWT token 生成、校验 工具类
* */
public class JWTUtil {

    // token 有效时长 24小时 过期
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    //  用于加密的密钥
    private static final String SECRET_KEY = "weilong";

    //  获得token的方法
    public static String getToken(UserBean user) {
        try {
            //  token有效时间 = 当前时间 + 有效时长
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            JwtBuilder jwtBuilder = Jwts.builder();
            String token = jwtBuilder
                    //  设置token header部分
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS256")
                    //  payload 可理解为存储数据的部分
                    .claim("username", user.getUsername())
                    .claim("userId", user.getUid())
                    .setExpiration(date)
                    //  签名部分 signature
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    //  将三个部分用.连接起来 字符串类型
                    .compact();
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //  根据密钥 验证token
    public static boolean verify(String token) {
        try {
            if (token == null) {
                return false;
            }
            JwtParser jwtParser = Jwts.parser();
            //  可正常解析，则token 使用的密钥正确
            Jws<Claims> claimsJws = jwtParser.setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //  获得用户信息
    public static String getUsername(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(SECRET_KEY).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String username = claims.get("username").toString();
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
