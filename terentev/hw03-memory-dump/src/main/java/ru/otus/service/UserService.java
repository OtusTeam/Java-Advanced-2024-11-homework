package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<String, byte[]> userCache = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String login, String password) {
        User user = new User(login, password);
        userRepository.save(user);
        userCache.put(login, new byte[520_000]);
    }

    public byte[] getUserData(String login) {
        return userCache.get(login);
    }
}
