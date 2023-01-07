package com.crud.config.auth.dto;

import lombok.Getter;

@Getter
public class Jwt {

    private String accessToken;
    private String refreshToken;
}
