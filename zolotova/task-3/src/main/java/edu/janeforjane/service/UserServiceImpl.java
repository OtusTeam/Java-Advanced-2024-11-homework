package edu.janeforjane.service;

import edu.janeforjane.datasource.UserRepository;
import edu.janeforjane.entities.User;
import edu.janeforjane.exceptions.LoginAlreadyExistsException;
import edu.janeforjane.exceptions.UnableSaveDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private Map<String, WeakReference<User>> cachedUsers = new HashMap<>();

    public void registerUser(String login, String password) throws LoginAlreadyExistsException, UnableSaveDataException {

        WeakReference<User> cachedUserRef = cachedUsers.get(login);
        if (cachedUserRef != null && cachedUserRef.get() != null) {
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
        input_user.setPassword(password);
        input_user.setLargeData(generateLargeData());

        //memory leak reason
        cachedUsers.put(login, new WeakReference<>(input_user));


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

    private byte[] generateLargeData() {
        //Generate big bytes array (1 MB)
        byte[] largeData = new byte[1024 * 1024];
        new Random().nextBytes(largeData);
        return largeData;
    }
}
