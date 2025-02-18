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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

//    private static long cnt = 0;
//    private static Lock lock = new ReentrantLock();

    private final Map<String, SoftReference<byte[]>> map = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UUID add(UserReq req, UUID runId) {
        User entity = userMapper.dtoToEntity(req);
        String login = entity.getLogin();

//        long l = countInc();
//        logger.info(String.format("User: runId: '%s', login: '%s' count: '%s'", runId, login, l));

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

//    private long countInc() {
//        lock.lock();
//        try {
//            cnt++;
//            return cnt;
//        } finally {
//            lock.unlock();
//        }
//    }

//        private void addToMap(String login, byte[] bytes) {
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
