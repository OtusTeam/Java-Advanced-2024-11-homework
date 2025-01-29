package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.dto.UserDto;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserCacheService userCacheService;

    public String register(UserDto userDto) {
        boolean exists =
                userRepository
                        .findAll()
                        .stream()
                        .anyMatch(u -> u.getLogin().equals(userDto.login()));

        if (exists) {
            throw new RuntimeException("User with this login already exists");
        }

        // add 100KB random data to the user
        byte[] randomData = new byte[100_000];
        ThreadLocalRandom.current().nextBytes(randomData);

        var user = User.builder()
                .login(userDto.login())
                .password(userDto.passwordHash())
                .data(randomData)
                .build();

        userRepository.save(user);
        log.info("Login: {} saved to DB", user.getLogin());
        userCacheService.putUser(user);
        log.info("Login: {} saved to Cache", user.getLogin());

        return "User registered successfully!";
    }

    public User getUser(Long id) {
        return userCacheService.getUser(id);
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        userCacheService.evictUser(id);
        return "User deleted and removed from cache";
    }
}
