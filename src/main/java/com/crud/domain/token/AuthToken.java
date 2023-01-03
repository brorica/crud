package com.crud.domain.token;

import java.time.LocalDateTime;
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
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    /**
     * 액세스 토큰 기간은 1시간
     */
    @Builder
    public AuthToken(Long uid, LocalDateTime dueDate, UUID accessToken, UUID refreshToken) {
        this.uid = uid;
        this.dueDate = dueDate.plusSeconds(3600);
        this.accessToken = accessToken.toString();
        this.refreshToken = refreshToken.toString();
    }
}
