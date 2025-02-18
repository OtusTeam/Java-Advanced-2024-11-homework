package org.example.task9.service;

import org.example.task9.models.UserReq;
import org.example.task9.persistence.entity.User;
import org.example.task9.persistence.mappers.UserMapper;
import org.example.task9.repository.UserRepository;
import org.example.task9.serviceExceptions.ServiceEntityExistRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserMapper userMapper;
    private UserRepository userRepository;

    private final Map<String, SoftReference<byte[]>> map = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UUID add(UserReq req, UUID runId) {
        User entity = userMapper.dtoToEntity(req);
        String login = entity.getLogin();

        SoftReference<byte[]> bytes = map.get(login);
        if(bytes != null && bytes.get() != null){
            userExist(runId, login);
        } else {
            Optional<User> byLogin = userRepository.findByLogin(login);
            if (byLogin.isPresent()) {
                addToMap(login, new byte[1000000]);
                userExist(runId, login);
            }
        }

        User save = userRepository.save(entity);
        UUID uuid = null;
        if(save != null) {
            uuid = save.getId();
            addToMap(login, new byte[1000000]);
        }
        logger.info(String.format("User add Done: runId: '%s', aggregateId: '%s'", runId, uuid));
        return uuid;
    }

//    private void addToMap(String login, byte[] bytes) {
//        try {
//            throw new Exception("Test add exception");
//        } catch (Exception e) {
//            map.put(login, new SoftReference<>(bytes));
//        }
//    }
    private void addToMap(String login, byte[] bytes) {
            map.put(login, new SoftReference<>(bytes));
    }

    private void userExist(UUID runId, String login) {
        String message = String.format("User add: runId: '%s', User with login: '%s' exist.", runId, login);
        logger.warn(message);
        throw new ServiceEntityExistRuntimeException(message);
    }
}
