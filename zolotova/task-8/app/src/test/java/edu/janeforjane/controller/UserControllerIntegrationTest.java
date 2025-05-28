package edu.janeforjane.controller;

import edu.janeforjane.datasource.UserRepository;
import edu.janeforjane.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        //Clean DB before each test
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterUser_OnlyResponse_Success() throws Exception {
        String login = "testUser";
        String password = "testPass";

        mockMvc.perform(post("/api/users/register")
                        .param("login", login)
                        .param("password", password))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser_ResultAndResponse_Success() throws Exception {
        String login = "testUser2";
        String password = "testPass";

        mockMvc.perform(post("/api/users/register")
                        .param("login", login)
                        .param("password", password))
                .andExpect(status().isOk());

        User user = userRepository.findByLogin(login).orElse(null);
        assertNotNull(user);
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws Exception {
        String login = "testUser";
        String password = "testPass";

        User existingUser = new User();
        existingUser.setLogin(login);
        existingUser.setPassword(password);
        userRepository.save(existingUser);

        mockMvc.perform(post("/api/users/register")
                        .param("login", login)
                        .param("password", password))
                .andExpect(status().is4xxClientError());
    }
}
