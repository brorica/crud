package com.crud.config.auth;

import static com.crud.config.auth.jwt.JwtManager.ACCESS_TOKEN_KEY;

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
     * TODO: jwt가 만료되면 파싱도 안 된다. DB의 스키마를 바꾸는 방향을 생각해야 할거 같다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(ACCESS_TOKEN_KEY);
        if (accessToken != null) {
            try {
                jwtManager.validate(accessToken);
            } catch (ExpiredJwtException e) {
                logger.info(accessToken);
                String accessTokenFromToken = jwtManager.getAccessTokenFromToken(accessToken);
                TokenDto newToken = expireHandler.checkRefreshToken(accessTokenFromToken);
                response.addHeader(ACCESS_TOKEN_KEY, jwtManager.createRefreshToken(newToken));
                logger.info(newToken.getAccessToken());
            }
        }
        filterChain.doFilter(request, response);
    }
}
