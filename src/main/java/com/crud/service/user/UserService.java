package com.crud.service.user;

import com.crud.domain.user.User;
import com.crud.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long getId(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email=" + email));
        return user.getId();
    }
}
