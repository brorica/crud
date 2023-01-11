package com.crud.domain.token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public AuthToken(Long uid, String accessToken, String refreshToken) {
        this.uid = uid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AuthToken updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
