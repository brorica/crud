package com.crud.config.auth.dto;

import com.crud.domain.user.User;
import java.io.Serializable;
import lombok.Getter;

/**
 * 원 개체인 User를 Serializable 하게 만드는 것은
 * 후에 생길 연관 관계 매핑에서 많은 위험이 따른다.
 * 따라서 Serializable한 서브 개체를 만들어서 관리한다.
 */
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
