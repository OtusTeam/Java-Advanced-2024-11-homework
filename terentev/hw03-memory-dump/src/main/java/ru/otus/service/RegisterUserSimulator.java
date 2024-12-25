package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RegisterUserSimulator {

    private final RestTemplate restTemplate;

    public RegisterUserSimulator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void simulateUserRegistration() {
        String url = "http://localhost:8080/users/register";
        String login = "user_" + System.currentTimeMillis();
        String password = "password";

        try {
            restTemplate.postForObject(
                    url + "?login=" + login + "&password=" + password,
                    null,
                    String.class
            );
            log.info("Simulated registration for: {}", login);
        } catch (Exception e) {
            log.error("Error during simulation: {}", e.getMessage());
        }
    }
}
