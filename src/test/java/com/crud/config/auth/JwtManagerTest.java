package com.crud.config.auth;
;
import static org.assertj.core.api.Assertions.assertThat;

import com.crud.domain.token.AuthToken;
import java.util.UUID;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtManagerTest extends TestCase {

    @Autowired
    JwtManager jwtManager;

    @Test
    public void jwt_토큰_생성() {
        //given
        AuthToken token = AuthToken.builder()
            .uid(1L)
            .accessToken(UUID.randomUUID())
            .refreshToken(UUID.randomUUID())
            .build();
        //when
        String jwt = jwtManager.createJwt(token);
        //then
        assertThat(jwt).isNotNull();
    }

    @Test
    public void jwt_토큰_검증() {
        //given
        AuthToken token = AuthToken.builder()
            .uid(1L)
            .accessToken(UUID.randomUUID())
            .refreshToken(UUID.randomUUID())
            .build();
        //when
        String jwt = jwtManager.createJwt(token);
        //then
        assertThat(jwtManager.validate(jwt)).isTrue();
    }
}