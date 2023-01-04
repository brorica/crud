package com.crud.config.auth;

import com.crud.domain.token.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtManager {

    private final long duration = 60 * 60 * 1000;
    private final String secret = "spring-boot-crud-practice-jwt-test-key";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String createJwt(AuthToken token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accessToken", token.getAccessToken());
        claims.put("refreshToken", token.getRefreshToken());
        Date now = new Date();
        return Jwts.builder()
            .setSubject("crud")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + duration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public Jwt<Header, Claims> validate(String jwt) {
        Jwt<Header, Claims> headerClaimsJwt = null;
        try {
            headerClaimsJwt = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(jwt);
        } catch (JwtException ex) {
        }
        return headerClaimsJwt;
    }
}
