package ru.otus.memory_dump_homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.service.RegistrationService;

@RestController
@RequestMapping("/registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        try {
            String userId = service.register(user);
            log.info("The user = {}, was saved successfully with id = {}", user, userId);
            return ResponseEntity.ok("The user was saved successfully with id = " + userId);
        } catch (Exception e) {
            log.error("Exception", e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("The user was saved unsuccessfully.");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDto> getUserById(
            @RequestParam(name = "userId") String userId
    ) {
        try {
            UserDto user = service.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound()
                    .build();
        }
    }

}
