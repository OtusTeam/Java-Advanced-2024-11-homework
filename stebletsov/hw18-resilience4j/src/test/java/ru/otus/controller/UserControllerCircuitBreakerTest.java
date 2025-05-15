package ru.otus.controller;

import io.github.resilience4j.springboot3.ratelimiter.autoconfigure.RateLimiterAutoConfiguration;
import io.github.resilience4j.springboot3.ratelimiter.autoconfigure.RateLimiterMetricsAutoConfiguration;
import io.github.resilience4j.springboot3.ratelimiter.autoconfigure.RateLimitersHealthIndicatorAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import ru.otus.client.RateLimiterMinute;
import ru.otus.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "spring.autoconfigure.exclude=" +
                        "io.github.resilience4j.springboot3.ratelimiter.autoconfigure.RateLimiterAutoConfiguration," +
                        "io.github.resilience4j.springboot3.ratelimiter.autoconfigure.RateLimiterMetricsAutoConfiguration," +
                        "io.github.resilience4j.springboot3.ratelimiter.autoconfigure.RateLimitersHealthIndicatorAutoConfiguration"
        }
)
public class UserControllerCircuitBreakerTest  {

    @MockitoBean
    private RateLimiterMinute rateLimiterMinute;

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    void shouldOpenCircuitBreakerAfterErrors() throws Exception {
        doThrow(new RuntimeException("Service failure"))
                .when(rateLimiterMinute).callRateLimiterApi();

        for (int i = 0; i < 10; i++) {
            try {
                restClient.get()
                        .uri("/api/users/1/age")
                        .retrieve()
                        .toEntity(Integer.class);
            } catch (Exception ignored) { }
            Thread.sleep(1000);
        }

        int code = 0;
        String message = "";
        try {
            ResponseEntity<Integer> response = restClient.get()
                    .uri("/api/users/1/age")
                    .retrieve()
                    .toEntity(Integer.class);
        } catch (RestClientResponseException rcre) {
            code = rcre.getStatusCode().value();
            message = rcre.getMessage();
        }

        assertEquals(503, code);
        assertEquals("503 Service Unavailable: \"Service is not available (CircuitBreaker open)\"", message);
    }
}
