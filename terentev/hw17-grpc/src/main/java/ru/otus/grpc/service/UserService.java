package ru.otus.grpc.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.grpc.entity.User;
import ru.otus.grpc.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Void> register(String login, String email, String password) {
        String hashedPassword = hashPassword(password, "SHA-256");
        User user = new User(login, email, hashedPassword);
        return userRepository.save(user).then();
    }

    public Mono<User> getByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    public Flux<String> getAllNames() {
        return userRepository.findAll()
                .map(User::getLogin);
    }

    public Flux<String> getValidEmails() {
        return userRepository.findValidEmails();
    }

    private String hashPassword(String password, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Invalid hashing algorithm: " + algorithm, e);
        }
    }
}
