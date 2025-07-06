package ru.otus.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.client.ClientAdapter;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    @Autowired
    private ClientAdapter clientAdapter;

    public Integer getAgeByUserId(Long userId) {
        return clientAdapter.circuitBreakerApi();
    }
}
