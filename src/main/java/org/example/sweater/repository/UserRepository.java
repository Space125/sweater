package org.example.sweater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.sweater.domain.User;

/**
 * @author Ivan Kurilov on 22.04.2021
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
