package ru.otus.client;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.exception.RateLimitExceededException;

@Component
@Slf4j
public class RateLimiterMinute {

    private final ClientRest clientRest;

    public RateLimiterMinute(ClientRest clientRest) {
        this.clientRest = clientRest;
    }

    @RateLimiter(name = "RPMRateLimiter", fallbackMethod = "rateLimiterFallback")
    public Integer callRateLimiterApi() {
        return clientRest.callApi();
    }

    public Integer rateLimiterFallback(Throwable t) {
        log.warn("Fallback from RPMRateLimiter triggered: {}", t.toString());
        throw new RateLimitExceededException("Too many requests per minute");
    }
}
