package ru.otus.memory_dump_homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Зарегистрировать пользователя",
            description = "Регистрирует пользователя и возвращает id пользователя при успешной регистарции"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<String> register(
            @Parameter(
                    name = "user",
                    description = "Пользователь",
                    required = true,
                    example = """
                            {
                                "login": "login",
                                "password": "password"
                            }
                    """
            )
            @RequestBody UserDto user
    ) {
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
    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает пользователя по заданному ID, если он существует"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<UserDto> getUserById(
            @Parameter(
                    name = "userId",
                    description = "Id пользователя",
                    required = true
            )
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
