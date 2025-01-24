package ru.otus.memory_dump_homework.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public String register(UserDto user) {
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
