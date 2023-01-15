package com.crud.config.auth;

import com.crud.config.auth.dto.CustomOauth2User;
import com.crud.config.auth.jwt.JwtManager;
import com.crud.domain.token.AuthToken;
import com.crud.domain.token.AuthTokenRepository;
import com.crud.service.user.UserService;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
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

    private final JwtManager jwtManager;
    private final AuthTokenRepository authTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        AuthToken authToken = saveToken(authentication);
        String targetUrl = determineTargetUrl(request, response, authToken);
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private AuthToken saveToken(Authentication authentication) {
        Long uid = parseUid(authentication);
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        AuthToken authToken = new AuthToken().builder()
            .uid(uid)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        return authTokenRepository.save(authToken);
    }

    private Long parseUid(Authentication authentication) {
        CustomOauth2User userDetails = (CustomOauth2User) authentication.getPrincipal();
        return userDetails.getUid();
    }

    protected String determineTargetUrl(HttpServletRequest request,
        HttpServletResponse response, AuthToken authToken) {
        String targetUrl = getDefaultTargetUrl();
        String accessTokenJwt = jwtManager.createAccessToken(authToken.getAccessToken());
        String refreshTokenJwt = jwtManager.createRefreshToken(authToken.getRefreshToken());

        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("access", accessTokenJwt)
            .queryParam("refresh", refreshTokenJwt)
            .build().toUriString();
    }
}
