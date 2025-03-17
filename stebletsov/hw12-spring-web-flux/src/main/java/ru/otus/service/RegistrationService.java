package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.UserDto;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;
import ru.otus.util.PasswordHashUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserRepository userRepository;

    public Mono<Void> register(UserDto userDto) {
        return userRepository.existsByLogin(userDto.login())
                .flatMap(exists -> {

            if (exists) {
                return Mono.error(new RuntimeException("User with this login already exists"));
            }

            String hashedPassword = PasswordHashUtil.hashPassword(userDto.password(), "SHA-256");

            var user = User.builder()
                    .login(userDto.login())
                    .password(hashedPassword)
                    .email(userDto.email())
                    .build();

            return userRepository.save(user)
                    .doOnSuccess(savedUser -> log.info("Login: {} password: {} saved to DB", savedUser.getLogin(), savedUser.getPassword()))
                    .doOnSuccess(cachedUser -> log.info("Login: {} saved to Cache", cachedUser.getLogin()))
                    .then();
        });
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono <Collection<String>> getAllUserNames() {
        return userRepository.findAll()
                .map(User::getLogin)
                .collectList()
                .map(ArrayList::new);
    }

    public Mono <Collection<String>> getAllEmails() {
        return userRepository.findAllEmails()
                .collectList()
                .map(ArrayList::new);
    }
}
