package com.crud.config.auth;

import static com.crud.config.auth.jwt.JwtManager.AUTHORIZATION_KEY;

import com.crud.config.auth.dto.TokenDto;
import com.crud.config.auth.jwt.JwtExpiredExceptionHandler;
import com.crud.config.auth.jwt.JwtManager;
import com.crud.config.auth.jwt.JwtParser;
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
    private final JwtParser jwtParser;
    private final JwtExpiredExceptionHandler jwtExpiredExceptionHandler;

    /**
     * TODO
     * 1. 토큰 발급과 갱신을 객체로 만들어서 처리할 수 있는지 생각하기
     * 2. refresh 토큰도 단순히 만료된 경우 어떻게 할지 생각하기
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String authorizeJwt = request.getHeader(AUTHORIZATION_KEY);
        if (authorizeJwt == null) {
            // TODO 로그인 창으로 리다이렉트 처리
            filterChain.doFilter(request, response);
            return;
        }
        checkAuthorizeJwt(request, response, authorizeJwt);
        filterChain.doFilter(request, response);
    }

    private void checkAuthorizeJwt(HttpServletRequest request, HttpServletResponse response, String authorizeJwt) {
        try {
            jwtParser.getAccessTokenFromToken(authorizeJwt);
        } catch (ExpiredJwtException e) {
            String accessToken = (String)e.getClaims().get(AUTHORIZATION_KEY);
            AuthToken authToken = jwtExpiredExceptionHandler.doHandle(request, accessToken);
            response.addHeader(AUTHORIZATION_KEY, jwtManager
                .createAccessToken(new TokenDto(authToken)));
        }
    }
}
