package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String login, @RequestParam String password) {
        userService.registerUser(login, password);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/data")
    public ResponseEntity<byte[]> findData(@RequestParam String login) {
        byte[] data = userService.getUserData(login);
        return ResponseEntity.ok(data);
    }
}
