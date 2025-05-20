package ru.otus.memory_dump_homework.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;

public interface RegistrationService {

    Mono<User> register(Mono<UserDto> user);

    Mono<UserDto> getUserById(Long userId);

    Flux<UserDto> getAllUsers();
}
