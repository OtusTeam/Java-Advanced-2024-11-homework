package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCacheService {
    private final UserRepository userRepository;
    private static final ConcurrentSkipListMap<Long, WeakReference<User>> userCache =
            new ConcurrentSkipListMap<>();
    private static final int threshold = 20;

    public User getUser(Long id) {
        var ref = userCache.get(id);
        User user = (ref != null) ? ref.get() : null;
        if (user == null) {
            user = userRepository.findById(id).orElse(null);
        }
        return user;
    }

    public void putUser(User user) {
        log.info("Cache size: {}", userCache.size());
        if (user.getId() == null) {
            throw new RuntimeException("User.id must not be null");
        }
        if (userCache.size() > threshold) {
            // remove half elements
            IntStream.range(0, threshold/2)
                    .takeWhile(i -> !userCache.isEmpty())
                    .forEach(i -> userCache.pollFirstEntry());
        }
        userCache.put(user.getId(), new WeakReference<>(user));
    }

    public void evictUser(Long id) {
        userCache.remove(id);
    }
}
