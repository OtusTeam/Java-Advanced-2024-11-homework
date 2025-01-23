package ru.otus.hw.task03.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.task03.entity.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CacheService {

    private final Map<String, UserCache> cache = new HashMap<>();

    public void saveInCache(User user) {
        var userCache = new UserCache(
                user.getLogin().getBytes(),
                user.getPassword().getBytes(),
                LocalDateTime.now(),
                UUID.nameUUIDFromBytes(user.getLogin().getBytes()),
                UUID.nameUUIDFromBytes(user.getPassword().getBytes())
        );
        cache.put(user.getLogin(), userCache);
    }

    private record UserCache(
            byte[] login,
            byte[] password,
            LocalDateTime currentTime,
            UUID loginUuid,
            UUID passwordUuid
    ) {}
}
