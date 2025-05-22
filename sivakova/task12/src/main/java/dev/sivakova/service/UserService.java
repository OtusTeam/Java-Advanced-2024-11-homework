package dev.sivakova.service;

import dev.sivakova.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> saveUser(User user);

    Flux<User> getAllUsers();

    Flux<String> getAllUserNames();

    Flux<String> getAllNonEmptyEmails();
}
