package org.example.task10.service;

import org.example.task10.models.UserReq;
import org.example.task10.persistence.entity.User;
import org.example.task10.persistence.mappers.UserMapper;
import org.example.task10.repository.UserRepository;
import org.example.task10.serviceExceptions.ServiceEntityExistRuntimeException;
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

//        Optional<User> byLogin2 = userRepository.findByLogin(login);
//        if (byLogin2.isPresent()) {
//            User user = byLogin2.get();
//            logger.info(String.format("User: runId: '%s', login: '%s'", runId, user.getLogin()));
//        }

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

    private void addToMap(String login, byte[] bytes) {
        map.put(login, new SoftReference<>(bytes));
    }

    private void userExist(UUID runId, String login) {
        String message = String.format("User add: runId: '%s', User with login: '%s' exist.", runId, login);
        logger.warn(message);
        throw new ServiceEntityExistRuntimeException(message);
    }
}
