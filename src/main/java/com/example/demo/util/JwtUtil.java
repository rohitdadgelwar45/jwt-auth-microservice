package com.example.demo.util;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // token generating
    public String generateToken(User user) {

        List<String> scopes = RoleScopeMapper.getScopes(user.getRole());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getRole().name())
                .claim("scopes", scopes)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    //secret key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    
    @SuppressWarnings("unchecked")
    public List<String> extractScopes(String token) {
        return extractAllClaims(token).get("scopes", List.class);
    }

    
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    
    public boolean validateToken(String token) {

        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

}