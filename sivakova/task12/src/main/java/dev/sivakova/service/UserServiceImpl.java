package dev.sivakova.service;

import dev.sivakova.model.User;
import dev.sivakova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Flux<String> getAllUserNames() {
        return userRepository.findAll()
                .map(User::getName);
    }

    @Override
    public Flux<String> getAllNonEmptyEmails() {
        return userRepository.findAll()
                .map(User::getEmail)
                .filter(email -> email != null && !email.trim().isEmpty());
    }
}
