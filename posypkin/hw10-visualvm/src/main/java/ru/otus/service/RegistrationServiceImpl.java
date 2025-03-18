package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.model.UserDto;
import ru.otus.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public String register(UserDto user) {
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            synchronized (mapper) {
                repository.deleteAll();
            }
        }
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
