package com.crud.config.auth;

import com.crud.config.auth.dto.TokenUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class JwtManager {

    private final long serialVersionUID = 1L;
    private final long expire = 60 * 60;
    private String secretKey = "secret";

    public String createJwt(TokenUser tokenUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accessToken", tokenUser.getAccessToken());
        claims.put("refreshToken", tokenUser.getRefreshToken());
        return Jwts.builder()
            .setSubject("crud")
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(tokenUser.getExpire().atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
}
