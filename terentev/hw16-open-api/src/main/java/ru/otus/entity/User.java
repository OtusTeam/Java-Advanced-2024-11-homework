package ru.otus.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "app_users")
@Schema(description = "Пользователь системы")
public class User {

    @Id
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Логин", example = "user123")
    private String login;

    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @Schema(description = "Хэш пароля")
    private String passwordHash;

    public User(String login, String email, String passwordHash) {
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
