package dev.korolz.task3.service;

import dev.korolz.task3.model.User;
import dev.korolz.task3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Map<String, byte[]> userCache;
    private final Random random = new Random();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userCache = new HashMap<>();
    }

    public void registerUser(String login, String password) {
        User user = new User(login, password);
        userRepository.save(user);
        // Добавляем большой массив в кеш (2MB)
        userCache.put(login, new byte[2 * 1024 * 1024]);
    }

    public byte[] getUserData(String login) {
        // Создаем копию данных при каждом запросе, которая будет висеть в памяти
        byte[] originalData = userCache.get(login);
        if (originalData != null) {
            byte[] newData = new byte[originalData.length];
            System.arraycopy(originalData, 0, newData, 0, originalData.length);
            return newData;
        }
        return null;
    }
}