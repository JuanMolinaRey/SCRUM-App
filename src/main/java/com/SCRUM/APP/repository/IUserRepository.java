package com.SCRUM.APP.repository;

import com.SCRUM.APP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface IUserRepository extends JpaRepository<User, Long> {
//    Optional<User> getTasksByUsername(String username);
    Optional<User> findByUsername(String username);
}