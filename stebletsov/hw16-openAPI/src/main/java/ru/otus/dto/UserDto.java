package ru.otus.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO to register user")
public record UserDto(
        @Schema(description = "Login", example = "john_doe")
        String login,

        @Schema(description = "Password", example = "password123")
        String password,

        @Schema(description = "Email", example = "john@example.com")
        String email) {
}
