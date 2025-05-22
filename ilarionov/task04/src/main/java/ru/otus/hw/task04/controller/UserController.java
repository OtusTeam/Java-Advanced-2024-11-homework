package ru.otus.hw.task04.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.task04.dto.UserDto;
import ru.otus.hw.task04.entity.User;
import ru.otus.hw.task04.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public UserDto addUser(@RequestBody UserDto userDto) {
        var user = new User(userDto.login(), userDto.password());
        log.info("Saving: {}", user);
        return userService.save(user);
    }
}
