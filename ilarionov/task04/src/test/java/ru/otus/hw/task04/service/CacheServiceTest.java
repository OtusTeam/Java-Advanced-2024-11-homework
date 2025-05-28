package ru.otus.hw.task04.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.task04.entity.User;

@SpringBootTest(classes = CacheService.class)
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Test
    void cacheServiceShouldSaveUser() {
        User user = new User("abc", "cde");
        cacheService.saveInCache(user);
    }
}
