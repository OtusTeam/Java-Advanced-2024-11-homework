package org.example.task12.service;

import org.example.task12.hashutil.PasswordHash;
import org.example.task12.models.UserReq;
import org.example.task12.persistence.entity.User;
import org.example.task12.persistence.mappers.UserMapper;
import org.example.task12.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HexFormat;
import java.util.UUID;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Mono<User> add(UserReq req, UUID runId) {
        User entity = userMapper.dtoToEntity(req);
        byte[] passwordHash = PasswordHash.createPasswordHash(entity.getPassword(), "SHA-256");
        String s = HexFormat.of().formatHex(passwordHash);
        entity.setPassword(s);
        entity.setId(UUID.randomUUID());
        Mono<User> save = userRepository.save(entity);
        return save;
    }

    public Flux<User> all() {
        return userRepository.findAll();
    }

    public Flux<String > names() {

        return all().map(User::getLogin);
    }

    public Flux<String> emails() {
        return userRepository.findNoEmptyEmails();
    }
}
