package com.crud.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class JwtManager {

    private final String refreshToken = "refreshToken";
    private final String accessToken = "accessToken";
    private final long accessTokenDuration = 60 * 60 * 1000;    // 1 hour
    private final long refreshTokenDuration = 7 * 24 * 60 * 60 * 1000;  // 1 week
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createAccessToken(String token) {
        Date now = new Date();
        return Jwts.builder()
            .setSubject("crud")
            .claim(accessToken, token)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenDuration))
            .signWith(key)
            .compact();
    }

    public String createRefreshToken(String token) {
        Date now = new Date();
        return Jwts.builder()
            .setSubject("crud")
            .claim(refreshToken, token)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenDuration))
            .signWith(key)
            .compact();
    }

    public boolean validate(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token).getBody();
    }

    public String getAccessTokenFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(accessToken);
    }

    public String getRefreshTokenFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(refreshToken);
    }
}
