package com.crud.config.auth.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtManager jwtManager;

    @Autowired
    private final AuthTokenService authTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt == null) {
            throw new IllegalArgumentException();
        }
        if (!jwtManager.validate(jwt)) {
            String refreshToken = jwtManager.getRefreshTokenFromToken(jwt);
            authTokenService.updateAccessToken(refreshToken);
        }
        filterChain.doFilter(request, response);
    }
}
