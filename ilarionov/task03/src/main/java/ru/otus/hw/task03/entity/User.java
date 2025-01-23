package ru.otus.hw.task03.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "app_user", schema = "cache")
@NoArgsConstructor
@Getter
@ToString
public class User {

    @Id
    @GeneratedValue(generator = "seq_user_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_user_id")
    private Long id;

    private String login;

    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
