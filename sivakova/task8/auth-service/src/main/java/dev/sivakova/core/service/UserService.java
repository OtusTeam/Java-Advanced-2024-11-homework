package dev.sivakova.core.service;

import java.util.List;
import java.util.Optional;

import dev.sivakova.core.model.User;
import dev.sivakova.core.model.UserDto;
import dev.sivakova.core.repository.UserRepository;
import dev.sivakova.core.util.PasswordHasher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.getByName(userDto.getName());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }


        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(PasswordHasher.hash(userDto.getPassword(), PasswordHasher.Algorithm.SHA256, 10000));
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
