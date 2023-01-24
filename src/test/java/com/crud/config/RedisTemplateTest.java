package com.crud.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.crud.config.auth.dto.SessionUser;
import com.crud.domain.user.Role;
import com.crud.domain.user.User;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, SessionUser> redisTemplate;

    @Test
    public void 세션저장() {
        //given
        ValueOperations<String, SessionUser> valueOperations = redisTemplate.opsForValue();
        String key = UUID.randomUUID().toString();
        SessionUser saveSession = new SessionUser(User.builder()
            .name("name")
            .email("email")
            .picture("picture")
            .role(Role.GUEST)
            .build());
        //when
        valueOperations.set(key, saveSession);

        //then
        SessionUser findSession = valueOperations.get(key);
        assertThat(findSession.getName()).isEqualTo(saveSession.getName());
    }
}
