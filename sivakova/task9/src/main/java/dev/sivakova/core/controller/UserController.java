package dev.sivakova.core.controller;

import dev.sivakova.core.model.UserDto;
import dev.sivakova.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/users"})
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public void registerUser(@RequestBody UserDto userDto) {
        this.userService.saveUser(userDto);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping({"/{id}"})
    public UserDto getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}
