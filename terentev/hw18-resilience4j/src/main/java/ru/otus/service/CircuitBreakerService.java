package ru.otus.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerService {

    @CircuitBreaker(name = "default", fallbackMethod = "fallbackGetUserAge")
    public int getUserAge(Long userId) {
        return (int) (Math.random() * 100);
    }

    public int fallbackGetUserAge(Long userId, Throwable throwable) {
        return -1;
    }
}
