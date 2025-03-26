package ru.otus.memory_dump_homework;

import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;

public class TestUtils {

    public static User createUser() {
        return new User(1L, "login", "<PASSWORD>");
    }

    public static UserDto createUserDto() {
        return new UserDto("login", "<PASSWORD>");
    }
}
