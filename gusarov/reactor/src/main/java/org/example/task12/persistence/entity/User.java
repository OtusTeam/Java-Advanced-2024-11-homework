package org.example.task12.persistence.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Getter
@Setter
@Table(name = "task12_users", schema = "public")
public class User {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
