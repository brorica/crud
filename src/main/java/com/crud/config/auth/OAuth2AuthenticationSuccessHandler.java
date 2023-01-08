package com.crud.config.auth;

import com.crud.config.auth.dto.CustomUserDetail;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

    private final String redirectUri = "http://localhost:8080/auth";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        String url = makeRedirectUrl(parseEmail(authentication));
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String email) {
        return UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("email", email)
            .build().toUriString();
    }

    private String parseEmail(Authentication authentication) {
        DefaultOAuth2User userDetails = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = userDetails.getAttributes();
        return (String) attributes.get("email");
    }
}
