package dev.sivakova.core.service;

import dev.sivakova.core.cache.FileCache;
import dev.sivakova.core.model.UserDto;
import dev.sivakova.core.model.entity.Role;
import dev.sivakova.core.model.entity.User;
import dev.sivakova.core.repository.RoleRepository;
import dev.sivakova.core.repository.UserRepository;
import dev.sivakova.core.util.PasswordHasher;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileCache fileCache;

    public UserDto findByName(String name) {
        Optional<User> user = userRepository.getByName(name);
        try {
            if (user.isPresent()) {
                return toUserDto(user.get());
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } catch (Exception e) {
            log.error("Error finding user by name: {}", name, e);
            return null;
        }
    }

    @Transactional
    public void saveUser(UserDto userDto) {
        UserDto existingUser = findByName(userDto.getName());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }


        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(PasswordHasher.hash(userDto.getPassword(), PasswordHasher.Algorithm.SHA256, 10000));
        user.setRegistrationDate(LocalDateTime.now());

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .map(user -> {
                   var fileInMemory =  fileCache.get(user);
                    return toUserDto(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }


    public List<UserDto> getUsers() {
        return userRepository.findTop100ByOrderByRegistrationDateDesc().stream()
                .map(user -> {
                    var fileInMemory = fileCache.get(user);
                    return toUserDto(user);
                })
                .collect(Collectors.toList());
    }


    public List<String> getAllUserNames() {
        return userRepository.findAll().stream()
                .map(User::getName).collect(Collectors.toList());
    }


    public List<String> getAllNonEmptyEmails() {
        return userRepository.findAll().stream()
                .map(User::getName)
                .filter(email -> email != null && !email.trim().isEmpty())
                .collect(Collectors.toList());
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setDaysSinceRegistration(ChronoUnit.DAYS.between(user.getRegistrationDate(), LocalDateTime.now()));
        userDto.setRegisteredAt(user.getRegistrationDate().atZone(ZoneId.of("UTC")));
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return userDto;
    }

}
