package com.crud.service.token;

import com.crud.domain.token.AuthToken;
import com.crud.domain.token.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthTokenService {

    private final AuthTokenRepository authTokenRepository;

    public AuthToken getAuthToken(String accessToken) {
        AuthToken authToken = authTokenRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new IllegalArgumentException("해당 accessToken 와 일치하는 토큰이 없습니다."));
        return authToken;
    }

    public void save(AuthToken authToken) {
        authTokenRepository.save(authToken);
    }
}
