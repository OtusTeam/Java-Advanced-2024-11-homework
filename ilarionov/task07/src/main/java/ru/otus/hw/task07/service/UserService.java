package ru.otus.hw.task07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.task07.dto.UserDto;
import ru.otus.hw.task07.entity.User;
import ru.otus.hw.task07.model.HashAlgorithm;
import ru.otus.hw.task07.repository.UserRepository;

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
}
