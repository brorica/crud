package com.crud.config.auth.dto;

import com.crud.domain.user.Role;
import com.crud.domain.user.User;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String authorities;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
        String name, String authorities, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.authorities = authorities;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,
        String userNameAttributeName, Map<String, Object> attributes) {
        /**
         * oauth2-client의 경우 국내 기업에 대한 부분은 구현을 하지 않았기 때문에
         * registrationId 를 보고 구분해야 한다.
         */
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
            .name((String) response.get("name"))
            .authorities("naver")
            .email((String) response.get("email"))
            .picture((String) response.get("profile_image"))
            .attributes(response)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
        Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .authorities("google")
            .email((String) attributes.get("email"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    public User toEntity() {
        return User.builder()
            .name(name)
            .authorities(authorities)
            .email(email)
            .picture(picture)
            .role(Role.USER)
            .build();
    }
}
