package com.crud.config.auth.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter
public class CustomOauth2User extends DefaultOAuth2User {

    private Long uid;

    private String email;

    public CustomOauth2User(Long uid, String email,
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes, String nameAttributeKey) {
        super(authorities, attributes, nameAttributeKey);
        this.uid = uid;
        this.email = email;
    }
}
