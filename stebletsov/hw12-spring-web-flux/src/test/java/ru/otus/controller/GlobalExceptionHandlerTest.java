package ru.otus.controller;

import io.r2dbc.spi.R2dbcTimeoutException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.dto.UserDto;
import ru.otus.service.RegistrationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RegistrationController.class)
@Import({TestSecurityConfig.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private RegistrationService registrationService;

    @Test
    void shouldReturnBadRequest_WhenUserAlreadyExists() {
        UserDto userDto = new UserDto("test_login", "password123", "test@email.com");
        when(registrationService.register(userDto))
                .thenReturn(Mono.error(new RuntimeException("User with this login already exists")));

        webTestClient.post().uri("/api/register")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("User with this login already exists");
    }

    @Test
    void shouldReturnInternalServerError_WhenGenericErrorOccurs() {
        UserDto userDto = new UserDto("test_login", "password123", "test@email.com");
        when(registrationService.register(userDto))
                .thenReturn(Mono.error(new R2dbcTimeoutException("timeout exception")));

        webTestClient.post().uri("/api/register")
                .bodyValue(userDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .value(response -> assertThat(response).contains("Internal server error: timeout exception"));
    }
}