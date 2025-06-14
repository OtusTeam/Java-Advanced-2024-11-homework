package dev.sivakova.core.service;

import java.util.List;

import dev.sivakova.core.model.User;
import dev.sivakova.core.model.UserDto;
import dev.sivakova.core.repository.UserRepository;
import dev.sivakova.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(PasswordHasher.hash(userDto.getPassword(), PasswordHasher.Algorithm.SHA256, 10000));
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
