package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCacheService {
    private final UserRepository userRepository;
    private static final Map<Long, User> userCache = new ConcurrentHashMap<>();

    public User getUser(Long id) {
        var user = userCache.get(id);
        if (user == null) {
            user = userRepository.findById(id).orElse(null);
        }
        return user;
    }

    public void putUser(User user) {
        if (user.getId() == null) {
            throw new RuntimeException("User.id must not be null");
        }
        userCache.put(user.getId(), user);
    }

    public void evictUser(Long id) {
        userCache.remove(id);
    }
}
