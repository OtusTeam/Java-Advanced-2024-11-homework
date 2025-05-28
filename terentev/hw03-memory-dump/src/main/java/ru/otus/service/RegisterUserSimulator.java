package ru.otus.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
            System.out.println("Simulated registration for: " + login);
        } catch (Exception e) {
            System.err.println("Error during simulation: " + e.getMessage());
        }
    }
}
