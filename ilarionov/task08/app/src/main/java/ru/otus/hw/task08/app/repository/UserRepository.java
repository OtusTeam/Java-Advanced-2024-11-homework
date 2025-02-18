package ru.otus.hw.task08.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.task08.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
