package com.crud.config.auth.jwt;

import static com.crud.config.auth.jwt.JwtManager.SECRET_KEY;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public boolean validate(String token) {
        Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        return true;
    }

}
