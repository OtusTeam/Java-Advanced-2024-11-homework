package com.otus.java.advanced.repository;

import com.otus.java.advanced.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
