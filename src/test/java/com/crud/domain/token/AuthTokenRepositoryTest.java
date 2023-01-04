package com.crud.domain.token;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenRepositoryTest {

    @Autowired
    AuthTokenRepository authTokenRepository;

    @After
    public void cleanup() {
        authTokenRepository.deleteAll();
    }

    @Test
    public void 액세스_토큰_검증() {
        //given
        UUID accessToken = UUID.randomUUID();
        authTokenRepository.save(AuthToken.builder()
            .uid(1L)
            .accessToken(accessToken)
            .refreshToken(UUID.randomUUID())
            .build()
        );

        //when
        List<AuthToken> authTokenList = authTokenRepository.findAll();

        //then
        AuthToken getAuthToken = authTokenList.get(0);
        assertThat(getAuthToken.getAccessToken()).isEqualTo(accessToken.toString());
    }

    @Test
    public void 리프레시_토큰_검증() {
        //given
        UUID refreshToken = UUID.randomUUID();
        authTokenRepository.save(AuthToken.builder()
            .uid(1L)
            .accessToken(UUID.randomUUID())
            .refreshToken(refreshToken)
            .build()
        );

        //when
        List<AuthToken> authTokenList = authTokenRepository.findAll();

        //then
        AuthToken getAuthToken = authTokenList.get(0);
        assertThat(getAuthToken.getRefreshToken()).isEqualTo(refreshToken.toString());
    }
}