package dev.sivakova.controller;

import dev.sivakova.model.User;
import dev.sivakova.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public Mono<User> registerUser(@RequestBody User userDto) {
        return userServiceImpl.saveUser(userDto);
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping(value = "/names")
    public Mono<List<String>> getAllUserNames() {
        return userServiceImpl.getAllUserNames().collectList();
    }


    @GetMapping(value = "/emails")
    public Mono<List<String>> getNonEmptyEmails() {
        return userServiceImpl.getAllNonEmptyEmails().collectList();
    }
}

