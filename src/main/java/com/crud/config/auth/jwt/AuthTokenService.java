package com.crud.config.auth.jwt;

import com.crud.domain.token.AuthToken;
import com.crud.domain.token.AuthTokenRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthTokenService {

    @Autowired
    private final AuthTokenRepository authTokenRepository;

    public void updateAccessToken(String refreshToken) {
        AuthToken authToken = getAuthTokenToken(refreshToken);
        authToken.updateAccessToken();
    }

    private AuthToken getAuthTokenToken(String accessToken) {
        Optional<AuthToken> authToken = authTokenRepository.findByRefreshToken(accessToken);
        if (authToken.isPresent()) {
            return authToken.get();
        }
        throw new NoSuchElementException("token not found");
    }
}
