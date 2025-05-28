package edu.janeforjane.service;

import edu.janeforjane.datasource.UserRepository;
import edu.janeforjane.entities.User;
import edu.janeforjane.exceptions.LoginAlreadyExistsException;
import edu.janeforjane.exceptions.UnableSaveDataException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Disabled
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();
    private List<User> cachedUsers = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userService, "cachedUsers", cachedUsers);
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        String login = "testUser";
        String password = "testPass";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        userService.registerUser(login, password);

//        User cachedUser = cachedUsers.get(0);
//        assertNotNull(cachedUser);
//        assertEquals(login, cachedUser.getLogin());
//        assertEquals(password, cachedUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExistsInCache() {
        String login = "testUser";
        String password = "testPass";

        User existingUser = new User();
        existingUser.setLogin(login);
        cachedUsers.add(existingUser);

        LoginAlreadyExistsException exception = assertThrows(LoginAlreadyExistsException.class, () -> {
            userService.registerUser(login, password);
        });

        assertEquals("User with this login already exists in cache.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExistsInDatabase() {
        String login = "testUser";
        String password = "testPass";

        User existingUser = new User();
        existingUser.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(existingUser));

        LoginAlreadyExistsException exception = assertThrows(LoginAlreadyExistsException.class, () -> {
            userService.registerUser(login, password);
        });

        assertEquals("User with this login already exists in database.", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UnableSaveDataException() throws LoginAlreadyExistsException {
        String login = "testUser";
        String password = "testPass";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        doThrow(new RuntimeException("Unable to save data")).when(userRepository).save(any(User.class));

        UnableSaveDataException exception = assertThrows(UnableSaveDataException.class, () -> {
            userService.registerUser(login, password);
        });

        assertEquals("Unable to save data", exception.getMessage());

        verify(userRepository, times(1)).save(any(User.class));
    }
}
