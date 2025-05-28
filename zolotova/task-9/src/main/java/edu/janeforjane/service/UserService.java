package edu.janeforjane.service;

import edu.janeforjane.entities.User;
import edu.janeforjane.exceptions.LoginAlreadyExistsException;
import edu.janeforjane.exceptions.UnableSaveDataException;

import java.util.List;

public interface UserService {

    void registerUser(String login, String password) throws Exception;

    List<User> getAllUsers();

    void registerUserWithLock(String login, String password)  throws Exception;
}
