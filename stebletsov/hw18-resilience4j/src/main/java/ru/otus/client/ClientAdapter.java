package ru.otus.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.exception.CircuitBreakerOpenException;
import ru.otus.exception.RateLimitExceededException;

@Component
@Slf4j
public class ClientAdapter {

    private final RateLimiterMinute rateLimiterMinute;

    public ClientAdapter(RateLimiterMinute rateLimiterMinute) {
        this.rateLimiterMinute = rateLimiterMinute;
    }

    @RateLimiter(name = "RPSRateLimiter", fallbackMethod = "rateLimiterFallback")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "circuitBreakerFallback")
    public Integer circuitBreakerApi() {
        return rateLimiterMinute.callRateLimiterApi();
    }

    public Integer rateLimiterFallback(Throwable t) {
        log.warn("RateLimiter fallback triggered: {}", t.toString());
        if (t.getMessage().contains("per minute")) {
            throw (RateLimitExceededException) t;
        }

        throw new RateLimitExceededException("Too many requests per second");
    }

    public Integer circuitBreakerFallback(Throwable t) {
        log.warn("CircuitBreaker fallback triggered: {}", t.toString());

        if (t instanceof RateLimitExceededException) {
            log.debug("Rethrowing RateLimitExceededException from CB fallback: {}", t.toString());
            throw (RateLimitExceededException) t;
        }

        throw new CircuitBreakerOpenException("Service is not available (CircuitBreaker open)");
    }
}

