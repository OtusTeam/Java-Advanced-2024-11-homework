package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<String, byte[]> userCache;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userCache = new LinkedHashMap<>(50, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
                return this.size() > 50;
            }
        };
    }

    public void registerUser(String login, String password) {
        String hashedPassword = hashPassword(password, "SHA-256");
        User user = new User(login, hashedPassword);
        userRepository.save(user);
        userCache.put(login, new byte[520_000]);
    }

    public byte[] getUserData(String login) {
        return userCache.get(login);
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
