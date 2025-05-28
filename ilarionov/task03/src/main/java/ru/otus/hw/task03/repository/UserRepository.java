package ru.otus.hw.task03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.task03.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
