package edu.janeforjane.service;

import edu.janeforjane.entities.User;
import edu.janeforjane.exceptions.LoginAlreadyExistsException;
import edu.janeforjane.exceptions.UnableSaveDataException;
import edu.janeforjane.exceptions.UnableToHashPasswordException;

import java.util.List;

public interface UserService {

    void registerUser(String login, String password) throws LoginAlreadyExistsException, UnableSaveDataException, UnableToHashPasswordException;

    List<User> getAllUsers();
}
