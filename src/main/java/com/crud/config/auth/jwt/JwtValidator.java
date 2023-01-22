package com.crud.config.auth.jwt;

import static com.crud.config.auth.jwt.JwtManager.SECRET_KEY;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtValidator {

    public void validate(String authorizeJwt) {
        Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(authorizeJwt);
    }
}
