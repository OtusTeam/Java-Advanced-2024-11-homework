package ru.otus.memory_dump_homework.service;

import ru.otus.memory_dump_homework.model.UserDto;

public interface RegistrationService {

    String register(UserDto user);

    UserDto getUserById(String userId);
}
