package ru.otus.memory_dump_homework.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.memory_dump_homework.entity.User;
import ru.otus.memory_dump_homework.model.UserDto;
import ru.otus.memory_dump_homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    @Override
    public Mono<User> register(Mono<UserDto> user) {
        return user.map(u -> mapper.map(u, User.class))
                .flatMap(repository::save);
    }

    @Override
    public Mono<UserDto> getUserById(Long userId) {
        return repository.findById(userId)
                .map(user -> mapper.map(user, UserDto.class));
    }

    @Override
    public Flux<UserDto> getAllUsers() {
        return repository.findAll()
                .map(user -> mapper.map(user, UserDto.class));
    }
}
