package ru.otus.jmh.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.jmh.entity.User;

public interface UserRepository extends CrudRepository<User, String> {
}
