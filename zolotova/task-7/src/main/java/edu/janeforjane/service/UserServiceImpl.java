package edu.janeforjane.service;

import edu.janeforjane.datasource.UserRepository;
import edu.janeforjane.entities.User;
import edu.janeforjane.exceptions.UnableSaveDataException;
import edu.janeforjane.exceptions.LoginAlreadyExistsException;
import edu.janeforjane.exceptions.UnableToHashPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    private List<User> cachedUsers = new ArrayList<>();

    public void registerUser(String login, String password) throws LoginAlreadyExistsException, UnableSaveDataException, UnableToHashPasswordException {

        Optional<User> cachedUser = cachedUsers.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();

        if (cachedUser.isPresent()) {
            log.warn("User with this login already exists in cache.");
            throw new LoginAlreadyExistsException("User with this login already exists in cache.");
        }

        Optional<User> existingUser = userRepository.findByLogin(login);
        if (existingUser.isPresent()) {
            log.warn("User with this login already exists in database.");
            throw new LoginAlreadyExistsException("User with this login already exists in database.");
        }

        User input_user = new User();
        input_user.setLogin(login);
        input_user.setPassword(hashPassword(password));
        input_user.setLargeData(generateLargeData());

        //memory leak reason
//        cachedUsers.add(input_user);


        try {
            userRepository.save(input_user);
        } catch (Exception e) {
            throw new UnableSaveDataException(e.getMessage());
        }

        log.info("New user was saved: {}", input_user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private String hashPassword(String rawPassword) throws UnableToHashPasswordException {
        try {
            return hashService.hashMD5(rawPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new UnableToHashPasswordException(e.getMessage());
        }
    }

    private byte[] generateLargeData() {
        //Generate big bytes array (1 MB)
        byte[] largeData = new byte[1024 * 1024];
        new Random().nextBytes(largeData);
        return largeData;
    }
}
