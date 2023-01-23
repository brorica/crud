package com.crud.config.auth.dto;

import com.crud.domain.token.AuthToken;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class TokenDto implements Serializable {

    private Long uid;

    private String name;

    private String accessToken;

    private String refreshToken;

    public TokenDto(AuthToken authToken) {
        this.uid = authToken.getUid();
        this.name = authToken.getName();
        this.accessToken = authToken.getAccessToken();
        this.refreshToken = authToken.getRefreshToken();
    }
}
