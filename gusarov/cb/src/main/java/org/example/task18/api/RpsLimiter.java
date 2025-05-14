package org.example.task18.api;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.example.task18.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RpsLimiter {

    private final UserService service;

    public RpsLimiter(UserService service) {
        this.service = service;
    }

    @RateLimiter(name = "rps")
    public Integer year(UUID userId, UUID runId){
        return service.year(userId, runId);
    }
}
