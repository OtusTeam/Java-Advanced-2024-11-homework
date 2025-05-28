package ru.otus.hw.task07.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.task07.dto.UserDto;
import ru.otus.hw.task07.dto.UserRequest;
import ru.otus.hw.task07.service.UserService;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @SneakyThrows
    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserRequest userRequest) {
        log.info("Saving: {}", userRequest.login());
        return userService.save(userRequest.login(), userRequest.password().getBytes(StandardCharsets.UTF_8));
    }
}
