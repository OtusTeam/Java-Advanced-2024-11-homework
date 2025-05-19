package ru.otus.provider;

import ru.otus.model.UserDto;

public interface ProviderService {
    UserDto findById(String userId);
}
