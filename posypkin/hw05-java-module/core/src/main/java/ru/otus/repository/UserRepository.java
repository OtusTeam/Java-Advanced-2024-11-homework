package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.entity.User;

public interface UserRepository extends CrudRepository<User, String> {
}
