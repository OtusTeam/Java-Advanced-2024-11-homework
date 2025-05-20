package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.entity.User;
import ru.otus.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestParam String login, @RequestParam String email, @RequestParam String password) {
        return userService.register(login, email, password)
                .thenReturn(ResponseEntity.ok("User registered successfully"));
    }

    @GetMapping("/login")
    public Mono<ResponseEntity<User>> findByLogin(@RequestParam String login) {
        return userService.getByLogin(login)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public Flux<User> findAll() {
        return userService.getAll();
    }

    @GetMapping("/names")
    public Flux<String> findAllNames() {
        return userService.getAllNames();
    }

    @GetMapping("/emails")
    public Flux<String> findValidEmails() {
        return userService.getValidEmails();
    }
}
