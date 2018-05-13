package com.lany.repository;

import com.lany.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User类的CRUD操作
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
