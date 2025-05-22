package org.example.task4.service;

import org.example.task4.models.UserReq;
import org.example.task4.persistence.entity.User;
import org.example.task4.persistence.mappers.UserMapper;
import org.example.task4.repository.UserRepository;
import org.example.task4.serviceExceptions.ServiceEntityExistRuntimeException;
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

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final Map<String, SoftReference<byte[]>> map = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UUID add(UserReq req, UUID runId) {
        User entity = userMapper.dtoToEntity(req);
        String login = entity.getLogin();

        logger.info(String.format("After map login: '%s', password: '%s'", login, entity.getPassword()));

        SoftReference<byte[]> bytes = map.get(login);
        if(bytes != null && bytes.get() != null){
            logger.info(String.format("Cache data exist login: '%s'", login));
            userExist(runId, login);
        } else {
            logger.info(String.format("Get data from DB login: '%s'", login));
            Optional<User> byLogin = userRepository.findByLogin(login);
            if (byLogin.isPresent()) {
                logger.info(String.format("Add data to cache login: '%s'", login));
                map.put(login, new SoftReference<>(new byte[1000000]));
                userExist(runId, login);
            }
        }

        logger.info(String.format("Repository save login: '%s'", login));
        User save = userRepository.save(entity);
        UUID uuid = save.getId();
        map.put(login, new SoftReference<>(new byte[1000000]));

        logger.info(String.format("User add Done: runId: '%s', aggregateId: '%s'", runId, uuid));
        return uuid;
    }

    private void userExist(UUID runId, String login) {
        String message = String.format("User add: runId: '%s', User with login: '%s' exist.", runId, login);
        logger.warn(message);
        throw new ServiceEntityExistRuntimeException(message);
    }
}
