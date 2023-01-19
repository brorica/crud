package com.crud.config.auth.jwt;

import static com.crud.config.auth.jwt.JwtManager.ACCESS_TOKEN_KEY;
import static com.crud.config.auth.jwt.JwtManager.REFRESH_TOKEN_KEY;
import static com.crud.config.auth.jwt.JwtManager.SECRET_KEY;
import static com.crud.config.auth.jwt.JwtManager.UID_KEY;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtParser {

    private Claims getAllClaimsFromToken(String jwt) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(jwt).getBody();
    }

    public String getAccessTokenFromToken(String jwt) {
        Claims claims = getAllClaimsFromToken(jwt);
        return (String) claims.get(ACCESS_TOKEN_KEY);
    }

    public String getRefreshTokenFromToken(String jwt) {
        Claims claims = getAllClaimsFromToken(jwt);
        return (String) claims.get(REFRESH_TOKEN_KEY);
    }

    public Long getUidFromToken(String jwt) {
        Claims claims = getAllClaimsFromToken(jwt);
        return (Long) claims.get(UID_KEY);
    }
}