package ru.otus.hw.task03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.task03.dto.UserDto;
import ru.otus.hw.task03.entity.User;
import ru.otus.hw.task03.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final CacheService cacheService;

    public UserDto save(User user) {
        User savedUser = userRepository.save(user);
        cacheService.saveInCache(savedUser);
        return new UserDto(savedUser.getId(), savedUser.getLogin(), savedUser.getPassword());
    }
}
