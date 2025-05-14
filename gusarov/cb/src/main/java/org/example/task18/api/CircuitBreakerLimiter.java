package org.example.task18.api;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CircuitBreakerLimiter {

    private final RpmLimiter rpmLimiter;

    public CircuitBreakerLimiter(RpmLimiter rpmLimiter) {
        this.rpmLimiter = rpmLimiter;
    }

    @CircuitBreaker(name = "default")
    public Integer year(UUID userId, UUID runId){
        return rpmLimiter.year(userId, runId);
    }
}
