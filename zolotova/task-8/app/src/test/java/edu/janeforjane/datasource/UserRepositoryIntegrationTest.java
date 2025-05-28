package edu.janeforjane.datasource;

import edu.janeforjane.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testPass");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("testUser", savedUser.getLogin());
        assertEquals("testPass", savedUser.getPassword());
    }

    @Test
    public void testFindByLogin() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testPass");
        entityManager.persist(user);

        Optional<User> foundUser = userRepository.findByLogin("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getLogin());
        assertEquals("testPass", foundUser.get().getPassword());
    }

    @Test
    public void testFindByLogin_UserNotFound() {
        Optional<User> foundUser = userRepository.findByLogin("nonExistentUser");

        assertTrue(foundUser.isEmpty());
    }
}
