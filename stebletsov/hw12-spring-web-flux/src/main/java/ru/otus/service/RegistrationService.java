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

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserCacheService userCacheService;

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
                    .doOnSuccess(userCacheService::putUser)
                    .doOnSuccess(cachedUser -> log.info("Login: {} saved to Cache", cachedUser.getLogin()))
                    .then();
        });
    }

    public Mono<User> getUser(Long id) {
        return userCacheService.getUser(id);
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id)
                .then(Mono.fromRunnable(() -> userCacheService.evictUser(id)))
                .then();
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<String> getAllUserNames() {
        return userRepository.findAll().map(User::getLogin);
    }

    public Flux<String> getAllEmails() {
        return userRepository.findAllEmails();
    }
}
