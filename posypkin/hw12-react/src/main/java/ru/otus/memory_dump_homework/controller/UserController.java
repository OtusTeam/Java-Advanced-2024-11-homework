package ru.otus.memory_dump_homework.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.service.RegistrationService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final RegistrationService service;

    @GetMapping("/user/{id}")
    public Mono<UserDto> getUserById(
            @PathVariable(name = "id") Long userId
    ) {
        return service.getUserById(userId);
    }

    @GetMapping("/users")
    public Flux<UserDto> getAllUsers() {
        return service.getAllUsers();
    }
}
