package com.crud.service.user;

import com.crud.domain.user.AuthToken;
import com.crud.domain.user.User;
import com.crud.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public AuthToken getToken(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email=" + email));
        return new AuthToken(user.getAccessToken(), user.getRefreshToken());
    }

    public String getAccessToken(String accessToken) {
        User user = userRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new IllegalArgumentException(accessToken + "란 토큰은 없습니다."));
        return user.getAccessToken();
    }

    public String getRefreshToken(String refreshToken) {
        User user = userRepository.findByAccessToken(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException(refreshToken + "란 토큰은 없습니다."));
        return user.getRefreshToken();
    }
}
