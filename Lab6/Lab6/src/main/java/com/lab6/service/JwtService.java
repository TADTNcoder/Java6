package com.lab6.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    /**
     * Tạo JWT
     */
    public String create(UserDetails user, int expirySeconds) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(Map.of("name", "Poly"))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000L * expirySeconds))
                .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Lấy body của JWT
     */
    public Claims getBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Kiểm tra token còn hạn hay không
     */
    public boolean validate(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    /**
     * Khóa bí mật để ký và xác minh JWT
     * HS256 cần ít nhất 32 bytes
     */
    private Key getSigningKey() {
        String secret = "0123456789.0123456789.0123456789";
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
