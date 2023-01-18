package com.crud.config.auth;

import static com.crud.config.auth.jwt.JwtManager.ACCESS_TOKEN_KEY;
import static com.crud.config.auth.jwt.JwtManager.REFRESH_TOKEN_KEY;

import com.crud.config.auth.dto.TokenDto;
import com.crud.config.auth.jwt.ExpireHandler;
import com.crud.config.auth.jwt.JwtManager;
import com.crud.domain.token.AuthToken;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final ExpireHandler expireHandler;

    /**
     * TODO
     * 1. 토큰 발급과 갱신을 객체로 만들어서 처리할 수 있는지 생각하기
     * 2. refresh 토큰도 단순히 만료된 경우 어떻게 할지 생각하기
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String accessTokenHeaderValue = request.getHeader(ACCESS_TOKEN_KEY);
        if (accessTokenHeaderValue != null) {
            try {
                jwtManager.validate(accessTokenHeaderValue);
            } catch (ExpiredJwtException e) {
                String refreshTokenHeaderValue = request.getHeader(REFRESH_TOKEN_KEY);
                String accessToken = (String)e.getClaims().get(ACCESS_TOKEN_KEY);
                String refreshToken = jwtManager.getRefreshTokenFromToken(refreshTokenHeaderValue);
                logger.info("current : " + accessToken);
                TokenDto newToken = expireHandler.checkRefreshToken(accessToken, refreshToken);
                response.addHeader(ACCESS_TOKEN_KEY, jwtManager.createRefreshToken(newToken));
                logger.info("new : " + newToken.getAccessToken());
            }
        }
        filterChain.doFilter(request, response);
    }
}
