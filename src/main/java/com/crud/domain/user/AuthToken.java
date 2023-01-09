package com.crud.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthToken {

    private final String accessToken;
    private final String refreshToken;
}
