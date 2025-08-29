package ru.otus.memory_dump_homework.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        description = "Пользователь",
        name = "UserDto",
        requiredMode = Schema.RequiredMode.REQUIRED,
        accessMode = Schema.AccessMode.READ_ONLY,
        type = "object",
        title = "UserDto",
        example = """
                {
                    "login": "login",
                    "password": "password"
                }
        """
)
public class UserDto {

    @Schema(
            description = "Логин пользователя",
            example = "login",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_ONLY,
            type = "string"
    )
    public String login;

    @Schema(
            description = "Пароль пользователя",
            example = "password",
            requiredMode = Schema.RequiredMode.REQUIRED,
            accessMode = Schema.AccessMode.READ_ONLY,
            type = "string",
            format = "password"
    )
    public String password;
}
