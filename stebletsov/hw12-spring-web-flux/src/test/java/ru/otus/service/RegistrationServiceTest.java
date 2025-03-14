package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import reactor.test.StepVerifier;
import ru.otus.dto.UserDto;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import static org.mockito.Mockito.*;


class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCacheService userCacheService;

    @InjectMocks
    private RegistrationService registrationService;

    private final User user = new User(1L, "test_login", "password123", "test@email.com");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldSaveAndReturnUser() {
        UserDto userDto = new UserDto("test_login", "password123", "test@email.com");
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(userRepository.existsByLogin(any(String.class))).thenReturn(Mono.just(Boolean.FALSE));

        Mono<Void> result = registrationService.register(userDto);

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository, times(1)).existsByLogin(any(String.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUser_ShouldReturnUser() {
        when(userCacheService.getUser(1L)).thenReturn(Mono.just(user));

        Mono<User> result = registrationService.getUser(1L);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        when(userRepository.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = registrationService.deleteUser(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository, times(1)).deleteById(1L);
        verify(userCacheService, times(1)).evictUser(1L);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Flux.just(user));

        Flux<User> result = registrationService.getAllUsers();

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUserNames_ShouldReturnListOfNames() {
        when(userRepository.findAll()).thenReturn(Flux.just(user));

        Flux<String> result = registrationService.getAllUserNames();

        StepVerifier.create(result)
                .expectNext("test_login")
                .verifyComplete();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllEmails_ShouldReturnListOfEmails() {
        when(userRepository.findAllEmails()).thenReturn(Flux.just("test@email.com"));

        Flux<String> result = registrationService.getAllEmails();

        StepVerifier.create(result)
                .expectNext("test@email.com")
                .verifyComplete();

        verify(userRepository, times(1)).findAllEmails();
    }
}