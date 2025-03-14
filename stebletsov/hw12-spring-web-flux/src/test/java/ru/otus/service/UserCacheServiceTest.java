package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.entity.User;
import ru.otus.repository.UserRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class UserCacheServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCacheService userCacheService;

    private final User user = new User(1L, "test_login", "password123", "test@email.com");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userCacheService = new UserCacheService(userRepository);
    }

    @Test
    void getUser_ShouldReturnUserFromCache() {
        userCacheService.putUser(user);

        StepVerifier.create(userCacheService.getUser(1L))
                .expectNext(user)
                .verifyComplete();

        verifyNoInteractions(userRepository);
    }

    @Test
    void getUser_ShouldLoadFromDatabaseAndCache() {
        when(userRepository.findById(1L)).thenReturn(Mono.just(user));

        StepVerifier.create(userCacheService.getUser(1L))
                .expectNext(user)
                .verifyComplete();

        verify(userRepository, times(1)).findById(1L);
        assertThat(userCacheService.getUser(user.getId()).block()).isEqualTo(user);
    }

    @Test
    void getUser_ShouldReturnErrorWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Mono.empty());

        StepVerifier.create(userCacheService.getUser(999L))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("User not found id: 999"))
                .verify();

        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void putUser_ShouldStoreUserInCache() {
        userCacheService.putUser(user);

        assertThat(userCacheService.getUser(user.getId()).block()).isEqualTo(user);
    }

    @Test
    void putUser_ShouldThrowExceptionWhenIdIsNull() {
        User invalidUser = new User(null, "user_without_id", "pass", "test@email.com");

        assertThatThrownBy(() -> userCacheService.putUser(invalidUser))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User.id must not be null");
    }

    @Test
    void evictUser_ShouldRemoveUserFromCache() {
        when(userRepository.findById(1L)).thenReturn(Mono.empty());

        userCacheService.putUser(user);
        userCacheService.evictUser(1L);

        StepVerifier.create(userCacheService.getUser(1L))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("User not found id: 1"))
                .verify();

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void putUser_ShouldRemoveHalfWhenThresholdExceeded() {
        var userCache = new UserCacheService(userRepository);
        IntStream.rangeClosed(1, 25).forEach(i ->
                userCache.putUser(new User((long) i, "user" + i, "pass" + i, "email" + i)));

        assertThat(userCache.getSize()).isEqualTo(15);
    }
}