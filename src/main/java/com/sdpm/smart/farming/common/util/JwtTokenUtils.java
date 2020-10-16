package com.sdpm.smart.farming.common.util;

import com.sdpm.smart.farming.devicemgr.dto.SfUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

/**
 * @author shirukai
 */
public class JwtTokenUtils {

    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:de287d0d
     */
    public static final String SECRET = "de287d0d";

    /**
     * token 过期时间: 10天
     */
    public static final int CALENDAR_FIELD = Calendar.DATE;
    public static final int CALENDAR_INTERVAL = 10;

    /**
     * 生成Token
     *
     * @param user 用户信息
     */
    public static String createToken(SfUserDTO user) {
        // expire time
        Calendar calendar = Calendar.getInstance();
        calendar.add(CALENDAR_FIELD, CALENDAR_INTERVAL);
        Date expiresDate = calendar.getTime();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        claims.put("password", user.getPassword());
        // build token
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                //jwt的签发时间
                .setIssuedAt(new Date())
                // 设置签名，使用的是签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, SECRET)
                // 设置过期时间
                .setExpiration(expiresDate)
                .compact();

    }

    public static Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token).getBody();
            if (claims.getExpiration().after(new Date())) {
                return claims;
            }
        } catch (JwtException e) {
            // nothing todo
            e.printStackTrace();
        }
        return null;

    }

}