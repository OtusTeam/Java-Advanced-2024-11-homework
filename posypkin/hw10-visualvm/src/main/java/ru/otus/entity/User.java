package ru.otus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "\"user\"")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    public String id;
    public String login;
    public String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
