package com.crud.config.auth.jwt;

import static com.crud.config.auth.jwt.JwtManager.REFRESH_TOKEN_KEY;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtResolver {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * jwt 토큰에 Bearer 붙여서 파싱할 예정
     */
    public String resolveAuthorizeToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        return bearerToken;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader(REFRESH_TOKEN_KEY);
    }
}
