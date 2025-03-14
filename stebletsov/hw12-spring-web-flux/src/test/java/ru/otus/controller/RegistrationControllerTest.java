package ru.otus.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.UserDto;
import ru.otus.entity.User;
import ru.otus.service.RegistrationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RegistrationController.class)
@Import(TestSecurityConfig.class)
class RegistrationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private RegistrationService registrationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final User user = new User(1L, "test_login", "password123", "test@email.com");

    @Test
    void registerUser_ShouldReturnSuccessMessage() {
        UserDto userDto = new UserDto("test_login", "password123", "test@email.com");
        when(registrationService.register(userDto)).thenReturn(Mono.empty());

        webTestClient.post().uri("/api/register")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertThat(response).isEqualTo("User registered successfully!"));
    }

    @Test
    void getUser_ShouldReturnUser() throws JsonProcessingException {
        when(registrationService.getUser(anyLong())).thenReturn(Mono.just(user));
        String expectedJson = objectMapper.writeValueAsString(user);

        webTestClient.get().uri("/api/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(expectedJson);

        verify(registrationService, times(1)).getUser(1L);
    }

    @Test
    void getUser_ShouldReturnNotFound() {
        when(registrationService.getUser(1L)).thenReturn(Mono.empty());

        webTestClient.get().uri("/api/user/1")
                .exchange()
                .expectStatus().isNotFound();

        verify(registrationService, times(1)).getUser(1L);
    }

    @Test
    void deleteUser_ShouldReturnOk() {
        when(registrationService.deleteUser(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/user/1")
                .exchange()
                .expectStatus().isOk();

        verify(registrationService, times(1)).deleteUser(1L);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(registrationService.getAllUsers()).thenReturn(Flux.just(user));

        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(1)
                .contains();

        verify(registrationService, times(1)).getAllUsers();
    }

    @Test
    void getAllUserNames_ShouldReturnListOfNames() {
        when(registrationService.getAllUserNames()).thenReturn(Flux.just("test_login"));

        webTestClient.get().uri("/api/users/names")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .hasSize(1)
                .contains("test_login");

        verify(registrationService, times(1)).getAllUserNames();
    }

    @Test
    void getAllEmails_ShouldReturnListOfEmails() {
        when(registrationService.getAllEmails()).thenReturn(Flux.just("test@email.com"));

        webTestClient.get().uri("/api/users/emails")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .hasSize(1)
                .contains("test@email.com");

        verify(registrationService, times(1)).getAllEmails();
    }
}
