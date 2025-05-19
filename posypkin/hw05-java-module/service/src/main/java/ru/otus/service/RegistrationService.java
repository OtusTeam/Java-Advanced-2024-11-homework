package ru.otus.service;

import ru.otus.model.UserDto;

public interface RegistrationService {

    String register(UserDto user);

    UserDto getUserById(String userId);
}
