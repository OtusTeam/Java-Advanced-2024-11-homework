package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

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
        User user = new User(login, password);
        userRepository.save(user);
        userCache.put(login, new byte[520_000]);
    }

    public byte[] getUserData(String login) {
        return userCache.get(login);
    }
}
