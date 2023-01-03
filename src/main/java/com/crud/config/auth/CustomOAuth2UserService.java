package com.crud.config.auth;

import com.crud.config.auth.dto.OAuthAttributes;
import com.crud.config.auth.dto.SessionUser;
import com.crud.domain.token.AuthToken;
import com.crud.domain.token.AuthTokenRepository;
import com.crud.domain.user.User;
import com.crud.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * oauth2를 통해 최종적으로 사용자 정보를 받아왔을 때 처리하는 곳
 * 유저 정보를 생성, 갱신하고 세션을 만든다.
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final AuthTokenRepository authTokenRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
            .getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes
            .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdateUser(attributes);
        AuthToken authToken = saveOrUpdateToken(user);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
            attributes.getAttributes(),
            attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdateUser(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
            .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

    private AuthToken saveOrUpdateToken(User user) {
        AuthToken token = AuthToken.builder()
            .uid(user.getId())
            .dueDate(LocalDateTime.now())
            .accessToken(UUID.randomUUID())
            .refreshToken(UUID.randomUUID())
            .build();
        authTokenRepository.save(token);
        return token;
    }
}
