package org.example.task18.api;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RpmLimiter {

    private final RpsLimiter rpsLimiter;

    public RpmLimiter(RpsLimiter rpsLimiter) {
        this.rpsLimiter = rpsLimiter;
    }

    @RateLimiter(name = "rpm")
    public Integer year(UUID userId, UUID runId){
        return rpsLimiter.year(userId, runId);
    }
}
