package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.service.CircuitBreakerService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CircuitBreakerService circuitBreakerService;

    @GetMapping("/{id}/age")
    public ResponseEntity<Integer> getUserAge(@PathVariable Long id) {
        int age = circuitBreakerService.getUserAge(id);
        return ResponseEntity.ok(age);
    }
}

