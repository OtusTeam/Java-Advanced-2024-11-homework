package ru.otus.hw.task08.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.task08.app.dto.UserDto;
import ru.otus.hw.task08.app.entity.User;
import ru.otus.hw.task08.app.model.HashAlgorithm;
import ru.otus.hw.task08.app.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final HashService hashService;

    @Transactional
    public UserDto save(String name, byte[] password) {
        String hash = hashService.getHash(password, HashAlgorithm.MD5);
        User user = new User(name, hash);
        userRepository.save(user);
        return new UserDto(user.getId(), user.getLogin(), user.getHash());
    }

    @Transactional
    public UserDto save(String name, byte[] password, HashAlgorithm algorithm) {
        String hash = hashService.getHash(password, algorithm);
        User user = new User(name, hash);
        userRepository.save(user);
        return new UserDto(user.getId(), user.getLogin(), user.getHash());
    }
}
