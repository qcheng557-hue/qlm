package com.qlm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final Key key;
    private final long expirationMillis;
    private final long refreshExpirationMillis;

    public JwtTokenUtil(@Value("${security.jwt.secret}") String secret,
                        @Value("${security.jwt.expiration}") long expiration,
                        @Value("${security.jwt.refresh-expiration}") long refreshExpiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expiration;
        this.refreshExpirationMillis = refreshExpiration;
    }

    public String generateToken(String subject) {
        return generateToken(subject, expirationMillis);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, refreshExpirationMillis);
    }

    private String generateToken(String subject, long ttlMillis) {
        Map<String, Object> claims = new HashMap<>();
        Date now = new Date();
        Date exp = new Date(now.getTime() + ttlMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
