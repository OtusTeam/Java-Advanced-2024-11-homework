package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<String, byte[]> userCache;
    private final Lock lock = new ReentrantLock();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userCache = new LinkedHashMap<>(50, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
                return this.size() > 100;
            }
        };
    }

    public synchronized void registerUser(String login, String password) {
//        lock.lock();
//        if (login == null || password == null) {
//            throw new IllegalArgumentException("Login or password cannot be null");
//        }
//
//        if (login.equals("error")) {
//            throw new RuntimeException("Exception for simulating error");
//        }

//        try {
            log.info("Registering user with login: {}", login);
            User user = new User(login, password);
            userRepository.save(user);
            userRepository.findById(user.getId());
            userCache.put(login, new byte[520_000]);
//        } finally {
//            lock.unlock();
//        }
    }

    public byte[] getUserData(String login) {
        return userCache.get(login);
    }
}
