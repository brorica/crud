package com.crud.config.auth;

import com.crud.config.auth.jwt.JwtManager;
import com.crud.domain.user.UserRepository;
import com.crud.service.user.UserService;
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
    private final UserService userService;
    private final String accessTokenName = "access-token";
    private final String refreshTokenName = "refresh-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String accessJwt = request.getHeader(accessTokenName);
        String accessTokenFromToken = jwtManager.getAccessTokenFromToken(accessJwt);
        if (jwtManager.validate(accessTokenFromToken)) {
            checkValidUser(accessTokenFromToken);
        }
        else {
            String refreshJwt = request.getHeader(refreshTokenName);
            String refreshTokenFromToken = jwtManager.getRefreshTokenFromToken(refreshJwt);
            checkRefreshToken(refreshTokenFromToken);
        }
        response.sendRedirect("/auth");
        filterChain.doFilter(request, response);
    }

    private void checkValidUser(String accessTokenName) {
        if (!userService.getAccessToken(accessTokenName).equals(accessTokenName)) {
            logger.info("access token 불일치");
        }
        logger.info("access token 일치");
    }

    private void checkRefreshToken(String refreshToken) {
        if (!userService.getRefreshToken(refreshToken).equals(refreshToken)) {
            logger.info("refresh token 불일치");
        }
        logger.info("refresh token 일치");
    }
}
