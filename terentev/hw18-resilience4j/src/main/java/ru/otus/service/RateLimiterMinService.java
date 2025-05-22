package ru.otus.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RateLimiterMinService {

    private final Random random = new Random();

    @RateLimiter(name = "rpm_limiter")
    public int getUserAge(Long userId) {
        return random.nextInt(100);
    }
}
