package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.UserDto;
import ru.otus.entity.User;
import ru.otus.service.RegistrationService;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody UserDto userDto) {
        return registrationService.register(userDto)
                .thenReturn(ResponseEntity.ok("User registered successfully!"));
    }

    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return registrationService.getAllUsers();
    }

    @GetMapping("/users/names")
    public Mono <Collection<String>> getAllUserNames() {
        return registrationService.getAllUserNames();
    }

    @GetMapping("/users/emails")
    public Mono <Collection<String>> getAllEmails() {
        return registrationService.getAllEmails();
    }
}
