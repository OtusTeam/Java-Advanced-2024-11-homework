package ru.otus.jmh.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.jmh.entity.User;
import ru.otus.jmh.repository.UserRepository;
import ru.otus.jmh.model.UserDto;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public String register(UserDto user) {
        var password = user.getPassword();
        var passwordHash = HashUtil.hashPassword(password, "MD5");
        user.setPassword(password);
        return repository.save(mapper.map(user, User.class))
                .getId();
    }

    @Override
    public UserDto getUserById(String userId) {
        return repository.findById(userId)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(IllegalStateException::new);
    }
}
