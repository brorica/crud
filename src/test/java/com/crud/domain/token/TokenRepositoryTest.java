package com.crud.domain.token;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
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
public class TokenRepositoryTest {

    @Autowired
    TokenRepository tokenRepository;

    @After
    public void cleanup() {
        tokenRepository.deleteAll();
    }

    @Test
    public void 액세스_토큰_검증() {
        //given
        UUID accessToken = UUID.randomUUID();
        tokenRepository.save(Token.builder()
            .dueDate(LocalDateTime.now())
            .accessToken(accessToken)
            .refreshToken(UUID.randomUUID())
            .build()
        );

        //when
        List<Token> tokenList = tokenRepository.findAll();

        //then
        Token getToken = tokenList.get(0);
        assertThat(getToken.getAccessToken()).isEqualTo(accessToken.toString());
    }

    @Test
    public void 리프레시_토큰_검증() {
        //given
        UUID refreshToken = UUID.randomUUID();
        tokenRepository.save(Token.builder()
            .dueDate(LocalDateTime.now())
            .accessToken(UUID.randomUUID())
            .refreshToken(refreshToken)
            .build()
        );

        //when
        List<Token> tokenList = tokenRepository.findAll();

        //then
        Token getToken = tokenList.get(0);
        assertThat(getToken.getRefreshToken()).isEqualTo(refreshToken.toString());
    }

    @Test
    public void 액세스_토큰_기한은_1시간() {
        //given
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.save(Token.builder()
            .dueDate(now)
            .accessToken(UUID.randomUUID())
            .refreshToken(UUID.randomUUID())
            .build()
        );

        //when
        List<Token> tokenList = tokenRepository.findAll();

        //then
        Token getToken = tokenList.get(0);
        assertThat(getToken.getDueDate()).isEqualTo(now.plusSeconds(3600));
    }
}