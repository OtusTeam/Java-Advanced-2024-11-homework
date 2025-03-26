package ru.otus.memory_dump_homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.repository.UserRepository;

import static ru.otus.memory_dump_homework.TestUtils.createUser;
import static ru.otus.memory_dump_homework.TestUtils.createUserDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactIT {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll().block();
    }

    @Test
    void getById() {
        var user = createUser();
        repository.save(user).map(
                u -> {
                    client.get()
                            .uri("/registration/getUser?userId=" + u.getId())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody()
                            .jsonPath("$").isNotEmpty()
                            .jsonPath("$.login").isEqualTo("login")
                            .jsonPath("$.password").isEqualTo("<PASSWORD>");
                    return u;
                }
        );
        System.out.println(repository.findById(1L).block());
    }

    @Test
    void register() {
        client.post()
                .uri("/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createUserDto()), UserDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.id").isNotEmpty();
    }

    @Test
    void getAllUser(){
        repository.save(new User(null, "test", "123456")).block();
        repository.save(new User(null, "jijdoiajs", "123456")).block();
        client.get()
                .uri("/registration/getAllUsers")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(2);

    }
}
