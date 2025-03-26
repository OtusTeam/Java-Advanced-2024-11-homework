package ru.otus.memory_dump_homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.service.RegistrationService;

@RestController
@RequestMapping("/registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping("/register")
    public Mono<User> register(@RequestBody Mono<UserDto> user) {
        return service.register(user)
                .map(u -> {
                    log.info("User {} registered", u);
                    return u;
                });
    }

    @GetMapping("/getUser")
    public Mono<UserDto> getUserById(
            @RequestParam(name = "userId") Long userId
    ) {
        return service.getUserById(userId);
    }

    @GetMapping("/getAllUsers")
    public Flux<UserDto> getAllUsers() {
        return service.getAllUsers()
                .map(p -> {
                    log.info(p.toString());
                    return p;
                });
    }

}
