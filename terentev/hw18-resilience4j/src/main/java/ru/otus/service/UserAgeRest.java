package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAgeRest {

    private final RateLimiterSecService rateLimiterSecService;
    private final RateLimiterMinService rateLimiterMinService;
    private final CircuitBreakerService circuitBreakerService;

    @GetMapping("/{id}/age/sec")
    public ResponseEntity<Integer> getUserAgeSec(@PathVariable Long id) {
        int age = rateLimiterSecService.getUserAge(id);
        return ResponseEntity.ok(age);
    }

    @GetMapping("/{id}/age/min")
    public ResponseEntity<Integer> getUserAgeMin(@PathVariable Long id) {
        int age = rateLimiterMinService.getUserAge(id);
        return ResponseEntity.ok(age);
    }

    @GetMapping("/{id}/age/cb")
    public ResponseEntity<Integer> getUserAgeCircuitBreaker(@PathVariable Long id) {
        int age = circuitBreakerService.getUserAge(id);
        return ResponseEntity.ok(age);
    }
}
