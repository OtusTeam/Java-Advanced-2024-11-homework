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
import ru.otus.dto.UserDto;
import ru.otus.entity.User;
import ru.otus.service.RegistrationService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        log.info("registerUser: {}", userDto);
        String result = registrationService.register(userDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id) {
        User user = registrationService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Login: " + user.getLogin() + ", Password: " + user.getPassword());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String msg = registrationService.deleteUser(id);
        return ResponseEntity.ok(msg);
    }
}
