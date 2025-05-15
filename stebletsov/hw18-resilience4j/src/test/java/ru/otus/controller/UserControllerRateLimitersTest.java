package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerRateLimitersTest  {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    void shouldLimitMoreThan20RequestsPerSecond() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(25);
        List<Future<ResponseEntity<Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            futures.add(executor.submit(() -> restClient.get()
                    .uri("/api/users/1/age")
                    .retrieve()
                    .toEntity(Integer.class)));
        }

        executor.shutdown();

        long tooManyPerSecond = futures.stream()
                .map(f -> {
                    try {
                        return f.get().getStatusCode().value();
                    } catch (RestClientResponseException | InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        Throwable cause = e.getCause();
                        if (cause instanceof RestClientResponseException rcre &&
                           rcre.getMessage().contains("Too many requests per second")) {
                            return rcre.getStatusCode().value();
                        }
                    }
                    return -1;
                })
                .peek(System.out::println)
                .filter(code -> code == 429)
                .count();

        assertTrue(tooManyPerSecond > 0);
    }

    @Test
    void shouldLimitMoreThan30RequestsPerMinute() throws Exception {
        int tooManyPerMinute = 0;

        for (int i = 0; i < 32; i++) {
            try {

                ResponseEntity<Integer> response = restClient.get()
                        .uri("/api/users/1/age")
                        .retrieve()
                        .toEntity(Integer.class);

            } catch (RestClientResponseException rcre) {

                System.out.println(rcre.getMessage());
                if (rcre.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS &&
                    rcre.getMessage().contains("Too many requests per minute")) {
                    tooManyPerMinute++;
                }
            }

            Thread.sleep(1000);
        }

        assertTrue(tooManyPerMinute > 0);
    }
}
