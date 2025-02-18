package ru.otus.hw.task03.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import ru.otus.hw.task03.entity.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CacheService {

    private final Cache<String, UserCache> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(1))
            .maximumSize(100)
            .build();

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
