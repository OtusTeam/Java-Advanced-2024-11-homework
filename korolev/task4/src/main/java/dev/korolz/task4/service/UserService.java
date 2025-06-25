package dev.korolz.task4.service;

import dev.korolz.task4.model.User;
import dev.korolz.task4.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<String, SoftReference<byte[]>> userCache;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        // LRU-кеш с ограничением в 100 элементов
        this.userCache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, SoftReference<byte[]>> eldest) {
                return size() > 100;
            }
        };
    }

    public void registerUser(String login, String password) {
        User user = new User(login, password);
        userRepository.save(user);
        // Добавляем большой массив в кеш (2MB)
        userCache.put(login, new SoftReference<>(new byte[2 * 1024 * 1024]));
    }

    public byte[] getUserData(String login) {
        SoftReference<byte[]> ref = userCache.get(login);
        return ref != null ? ref.get() : null;
    }
}