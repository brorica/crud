package com.crud.config.auth.jwt;

import com.crud.config.auth.dto.TokenDto;
import com.crud.domain.token.AuthToken;
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

    private final String UID_KEY = "uid";
    private final String REFRESH_TOKEN_KEY = "refreshToken";
    private final String ACCESS_TOKEN_KEY = "accessToken";
    private final long accessTokenDuration = 60 * 60 * 1000;    // 1 hour
    private final long refreshTokenDuration = 7 * 24 * 60 * 60 * 1000;  // 1 week
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createAccessToken(TokenDto token) {
        Date now = new Date();
        return Jwts.builder()
            .setSubject("crud")
            .claim(UID_KEY, token.getUid())
            .claim(ACCESS_TOKEN_KEY, token.getAccessToken())
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenDuration))
            .signWith(key)
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
        return (String) claims.get(ACCESS_TOKEN_KEY);
    }

    public String getRefreshTokenFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return (String) claims.get(REFRESH_TOKEN_KEY);
    }
}
