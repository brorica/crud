package com.crud.domain.token;

import java.util.UUID;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public AuthToken(Long uid, UUID accessToken, UUID refreshToken) {
        this.uid = uid;
        this.accessToken = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }

    public void updateAccessToken() {
        this.accessToken = UUID.randomUUID().toString();
    }
}
