package ru.otus.hw.task07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.task07.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
