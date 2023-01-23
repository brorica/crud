package com.crud.web;

import static com.crud.config.auth.jwt.JwtManager.AUTHORIZATION_KEY;
import static com.crud.config.auth.jwt.JwtManager.REFRESH_KEY;

import com.crud.config.auth.dto.CustomOauth2User;
import com.crud.config.auth.dto.TokenDto;
import com.crud.config.auth.jwt.JwtManager;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final JwtManager jwtManager;

    @GetMapping("/auth")
    public String auth(HttpServletResponse response, Authentication authentication) {
        CustomOauth2User userDetails = (CustomOauth2User) authentication.getPrincipal();
        TokenDto token = userDetails.getToken();
        response.addHeader(AUTHORIZATION_KEY, jwtManager.createAccessToken(token));
        response.addHeader(REFRESH_KEY, jwtManager.createRefreshToken(token));
        return "auth";
    }
}
