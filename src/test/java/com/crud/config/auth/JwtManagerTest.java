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
        AuthToken token = createToken(UUID.randomUUID(), UUID.randomUUID());
        //when
        String jwt = jwtManager.createJwt(token);
        //then
        assertThat(jwt).isNotNull();
    }

    @Test
    public void jwt_토큰_검증() {
        //given
        AuthToken token = createToken(UUID.randomUUID(), UUID.randomUUID());
        //when
        String jwt = jwtManager.createJwt(token);
        //then
        assertThat(jwtManager.validate(jwt)).isTrue();
    }

    @Test
    public void jwt_액세스_토큰_가져오기() {
        //given
        UUID accessToken = UUID.randomUUID();
        AuthToken token = createToken(accessToken, UUID.randomUUID());
        //when
        String jwt = jwtManager.createJwt(token);
        //then
        assertThat(jwtManager.getAccessTokenFromToken(jwt)).isEqualTo(accessToken.toString());
    }

    @Test
    public void jwt_리프레시_토큰_가져오기() {
        //given
        UUID refreshToken = UUID.randomUUID();
        AuthToken token = createToken(UUID.randomUUID(), refreshToken);
        //when
        String jwt = jwtManager.createJwt(token);
        //then
        assertThat(jwtManager.getRefreshTokenFromToken(jwt)).isEqualTo(refreshToken.toString());
    }

    private AuthToken createToken(UUID accessToken, UUID refreshToken) {
        return AuthToken.builder()
            .uid(1L)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}