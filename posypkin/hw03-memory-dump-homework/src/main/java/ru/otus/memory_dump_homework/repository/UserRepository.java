package ru.otus.memory_dump_homework.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.memory_dump_homework.entity.User;

public interface UserRepository extends CrudRepository<User, String> {
}
