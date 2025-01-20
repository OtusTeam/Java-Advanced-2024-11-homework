package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
