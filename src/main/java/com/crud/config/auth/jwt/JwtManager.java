package com.crud.config.auth.jwt;

import com.crud.config.auth.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtManager {

    public static final String UID_KEY = "uid";
    public static final String REFRESH_TOKEN_KEY = "refresh-token";
    public static final String ACCESS_TOKEN_KEY = "access-token";
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//     private final long accessTokenDuration = 60 * 60 * 1000;    // 1 hour
    private final long accessTokenDuration = 10;
    private final long refreshTokenDuration = 7 * 24 * 60 * 60 * 1000;  // 1 week

    public String createAccessToken(TokenDto token) {
        Date now = new Date();
        return Jwts.builder()
            .setSubject("crud")
            .claim(UID_KEY, token.getUid())
            .claim(ACCESS_TOKEN_KEY, token.getAccessToken())
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenDuration))
            .signWith(SECRET_KEY)
            .compact();
    }

    public String createRefreshToken(TokenDto token) {
        Date now = new Date();
        return Jwts.builder()
            .setSubject("crud")
            .claim(UID_KEY, token.getUid())
            .claim(REFRESH_TOKEN_KEY, token.getRefreshToken())
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenDuration))
            .signWith(SECRET_KEY)
            .compact();
    }
}
