package com.crud.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUserRepositoryTest {

    @Autowired
    private RedisUserRepository repository;

    @Test
    public void 레디스_테스트() {
        //given
        String name = "testName";
        repository.save(User.builder()
            .name(name)
            .email("email")
            .picture("picture")
            .role(Role.USER)
            .build()
        );

        //when
        List<User> users = (List<User>) repository.findAll();

        //then
        User user = users.get(0);
        assertThat(user.getName()).isEqualTo(name);
    }

}
