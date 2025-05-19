package otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.entity.User;
import ru.otus.service.UserCacheService;

import static org.junit.jupiter.api.Assertions.*;

class UserCacheServiceTest {

    private UserCacheService userCacheService;

    @BeforeEach
    void setUp() {
        userCacheService = new UserCacheService(null);
    }

    @Test
    void shouldGetUserFromCache() {
        User user = new User();
        user.setId(1L);
        user.setLogin("cached-user");

        userCacheService.putUser(user);
        User result = userCacheService.getUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("cached-user", result.getLogin());
    }

    @Test
    void shouldThrowAnError() {
        User user = new User();
        user.setLogin("no-id");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userCacheService.putUser(user));
        assertEquals("User.id must not be null", ex.getMessage());
    }
}

