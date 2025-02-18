package ru.otus.hw.task08.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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

    private String hash;

    private LocalDateTime time;

    public User(String login, String hash) {
        this.login = login;
        this.hash = hash;
        this.time = LocalDateTime.now();
    }
}
