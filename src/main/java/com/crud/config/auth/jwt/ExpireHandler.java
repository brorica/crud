package com.crud.config.auth.jwt;

import com.crud.config.auth.dto.TokenDto;
import com.crud.domain.token.AuthToken;
import com.crud.service.token.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExpireHandler {

    private final AuthTokenService authTokenService;

    public TokenDto checkRefreshToken(String accessToken, String refreshToken) {
        AuthToken token = authTokenService.getAuthToken(accessToken);
        if (refreshToken.equals(token.getRefreshToken())) {
            saveOrUpdateAccessToken(token);
        }
        return new TokenDto(token);
    }

    private void saveOrUpdateAccessToken(AuthToken token) {
        token.updateAccessToken();
        authTokenService.save(token);
    }
}
