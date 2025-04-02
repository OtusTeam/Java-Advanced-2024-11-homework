package ru.otus.entity;

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
public class User {

    @Id
    private Long id;

    private String login;
    private String email;
    private String passwordHash;

    public User(String login, String email, String passwordHash) {
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
