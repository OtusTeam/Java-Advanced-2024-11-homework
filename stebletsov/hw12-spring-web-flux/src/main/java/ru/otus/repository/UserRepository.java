package ru.otus.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.entity.User;

public interface UserRepository extends R2dbcRepository<User, Long> {
    Mono<Boolean> existsByLogin(String login);

    @Query("SELECT email FROM users WHERE email IS NOT NULL AND email <> ''")
    Flux<String> findAllEmails();
}
