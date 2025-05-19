package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import ru.otus.dto.UserDto;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class RegistrationSimulator {
    private final AtomicLong counter = new AtomicLong(1);
    private final ExecutorService executor =
            Executors.newVirtualThreadPerTaskExecutor();
    private final RestClient restClient = RestClient.builder().build();


    /**
     * Каждые 500 мс отправляем запрос POST /api/register
     */
    @Scheduled(fixedRate = 500)
    public void spamRegistrationRequests() {
        long i = counter.getAndIncrement();
        executor.submit(() -> doRequest(i));
    }

    private void doRequest(long i) {
        String url = "http://localhost:8080/api/register";
        UserDto userDto = new UserDto("user" + i, "pass" + i);

        try {
            String response = restClient
                    .post()
                    .uri(url)
                    .body(userDto)
                    .retrieve()
                    .body(String.class);

            log.info("Registration user number {} => {}", i, response);
        } catch (RestClientException e) {
            log.error("Failed to call /register: {}", e.getMessage(), e);
        }
    }
}
