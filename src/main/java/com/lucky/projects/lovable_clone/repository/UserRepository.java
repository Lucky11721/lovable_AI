package com.lucky.projects.lovable_clone.repository;

import com.lucky.projects.lovable_clone.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Optional<User> findByUsername(Long id);

    boolean existsByUsername(String username);
    Optional<Object> findByUsername(String username);
}
