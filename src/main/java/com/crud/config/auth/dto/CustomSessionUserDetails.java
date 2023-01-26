package com.crud.config.auth.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter
public class CustomSessionUserDetails extends DefaultOAuth2User {

    private final String sessionId;

    public CustomSessionUserDetails(
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes, String nameAttributeKey, String sessionId) {
        super(authorities, attributes, nameAttributeKey);
        this.sessionId = sessionId;
    }
}
