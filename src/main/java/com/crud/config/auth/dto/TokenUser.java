package com.crud.config.auth.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TokenUser {

    private LocalDateTime expire;
    private String accessToken;
    private String refreshToken;
}
