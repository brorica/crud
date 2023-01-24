package com.crud.domain.user;

import org.springframework.data.repository.CrudRepository;

public interface RedisUserRepository extends CrudRepository<User, String> {
}
