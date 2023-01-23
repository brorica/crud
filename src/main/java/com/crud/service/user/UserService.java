package com.crud.service.user;

import com.crud.domain.user.User;
import com.crud.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long getId(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email=" + email));
        return user.getId();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. email=" + id));
        return user;
    }
}
