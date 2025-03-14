package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
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
    private static final int THRESHOLD = 20;

    public int getSize() {
        return userCache.size();
    }

    public Mono<User> getUser(Long id) {
        return Mono.defer(() -> {
            var ref = userCache.get(id);
            User cachedUser = (ref != null) ? ref.get() : null;

            if (cachedUser != null) {
                return Mono.just(cachedUser);
            }

            return userRepository.findById(id)
                    .switchIfEmpty(Mono.error(new RuntimeException("User not found id: " + id)))
                    .doOnNext(this::putUser)
                    .doOnSuccess(user -> log.info("User {} loaded from DB and cached", user.getLogin()));
        });
    }

    public void putUser(User user) {
        log.info("Cache size: {}", userCache.size());
        if (user.getId() == null) {
            throw new RuntimeException("User.id must not be null");
        }
        if (userCache.size() > THRESHOLD) {
            // remove half elements
            IntStream.range(0, THRESHOLD /2)
                    .takeWhile(i -> !userCache.isEmpty())
                    .forEach(i -> userCache.pollFirstEntry());
        }
        userCache.put(user.getId(), new WeakReference<>(user));
    }

    public void evictUser(Long id) {
        userCache.remove(id);
    }
}
