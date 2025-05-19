package ru.otus.jmh.service;

import ru.otus.jmh.model.UserDto;

public interface RegistrationService {

    String register(UserDto user);

    UserDto getUserById(String userId);
}
