package ru.otus.memory_dump_homework.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.repository.UserRepository;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private final LinkedList<UserDto> userList = new LinkedList<>();

    @Override
    public String register(UserDto user) {
        userList.add(user);
        return repository.save(mapper.map(userList.getLast(), User.class))
                .getId();
    }

    @Override
    public UserDto getUserById(String userId) {
        return repository.findById(userId)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(IllegalStateException::new);
    }
}
