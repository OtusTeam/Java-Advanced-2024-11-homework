package ru.otus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Регистрация нового пользователя")
    public Mono<ResponseEntity<String>> register(
            @Parameter(description = "Логин", example = "user123") @RequestParam String login,
            @Parameter(description = "Email", example = "user@example.com") @RequestParam String email,
            @Parameter(description = "Пароль", example = "password123") @RequestParam String password) {
        return userService.register(login, email, password)
                .thenReturn(ResponseEntity.ok("User registered successfully"));
    }

    @GetMapping("/login")
    @Operation(summary = "Поиск пользователя по логину")
    public Mono<ResponseEntity<User>> findByLogin(
            @Parameter(description = "Логин", example = "user123") @RequestParam String login) {
        return userService.getByLogin(login)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    @Operation(summary = "Получить всех пользователей")
    public Flux<User> findAll() {
        return userService.getAll();
    }

    @GetMapping("/names")
    @Operation(summary = "Получить список логинов")
    public Flux<String> findAllNames() {
        return userService.getAllNames();
    }

    @GetMapping("/emails")
    @Operation(summary = "Получить список валидных email-ов")
    public Flux<String> findValidEmails() {
        return userService.getValidEmails();
    }
}
