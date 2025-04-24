package ru.otus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Registration and managing users")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Register a new use")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration data")
            @RequestBody UserDto userDto) {
        log.info("registerUser: {}", userDto);
        String result = registrationService.register(userDto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        User user = registrationService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Login: " + user.getLogin() + ", Password: " + user.getPassword());
    }

    @Operation(summary = "Delete user by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        String msg = registrationService.deleteUser(id);
        return ResponseEntity.ok(msg);
    }

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "User list successfully retrieved")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return registrationService.getAllUsers();
    }

    @Operation(summary = "Get all user names")
    @ApiResponse(responseCode = "200", description = "User names list successfully retrieved")
    @GetMapping("/users/names")
    public List <String> getAllUserNames() {
        return registrationService.getAllUserNames();
    }


    @Operation(summary = "Get all user emails")
    @ApiResponse(responseCode = "200", description = "User email list successfully retrieved") @GetMapping("/users/emails")
    public List <String> getAllEmails() {
        return registrationService.getAllEmails();
    }
}
