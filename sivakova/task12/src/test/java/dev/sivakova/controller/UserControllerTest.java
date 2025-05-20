package dev.sivakova.controller;

import dev.sivakova.model.User;
import dev.sivakova.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll().block();

        Flux<User> users = Flux.just(
                new User(UUID.randomUUID().toString(), "Alice", "password", "alice@mail.com"),
                new User(UUID.randomUUID().toString(), "Bob", "password",  ""),
                new User(UUID.randomUUID().toString(), "Charlie", "password", "charlie@mail.com")
        );

        userRepository.saveAll(users).blockLast();
    }

    @Test
    void shouldReturnAllUsers() {
        webTestClient.get()
                .uri("/users")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3);
    }

    @Test
    void shouldReturnUserNames() {
        webTestClient.get()
                .uri("/users/names")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[\"Alice\",\"Bob\",\"Charlie\"]");
    }

    @Test
    void shouldReturnOnlyNonEmptyEmails() {
        webTestClient.get()
                .uri("/users/emails")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[\"alice@mail.com\",\"charlie@mail.com\"]");
    }

    @Test
    void shouldRegisterNewUser() {
        User newUser = new User(UUID.randomUUID().toString(), "Diana", "password",  "diana@mail.com");

        webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .bodyValue(newUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Diana")
                .jsonPath("$.email").isEqualTo("diana@mail.com");
    }
}
