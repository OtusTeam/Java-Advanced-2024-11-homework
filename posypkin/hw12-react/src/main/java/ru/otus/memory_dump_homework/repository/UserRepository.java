package ru.otus.memory_dump_homework.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.memory_dump_homework.entity.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
