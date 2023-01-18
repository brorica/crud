package com.crud.config.auth;

import com.crud.config.auth.dto.CustomOauth2User;
import com.crud.config.auth.dto.TokenDto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        TokenDto token = parseToken(authentication);
        String targetUrl = determineTargetUrl(request, response, token);
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    private TokenDto parseToken(Authentication authentication) {
        CustomOauth2User userDetails = (CustomOauth2User) authentication.getPrincipal();
        return userDetails.getToken();
    }

    protected String determineTargetUrl(HttpServletRequest request,
        HttpServletResponse response, TokenDto token) {
        String targetUrl = getDefaultTargetUrl();
        return UriComponentsBuilder.fromUriString(targetUrl)
            .build().toUriString();
    }
}
