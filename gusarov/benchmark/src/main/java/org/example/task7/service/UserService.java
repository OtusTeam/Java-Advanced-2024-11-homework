package org.example.task7.service;

import org.example.task7.hashutil.PasswordHash;
import org.example.task7.models.UserReq;
import org.example.task7.persistence.entity.User;
//import org.example.task7.persistence.mappers.UserMapper;
import org.example.task7.repository.UserRepository;
import org.example.task7.serviceExceptions.ServiceEntityExistRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

//    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final Map<String, SoftReference<byte[]>> map = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${hash.alg}")
    private String hash_alg;

    public UserService( UserRepository userRepository){
//    public UserService(UserMapper userMapper, UserRepository userRepository){
//        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UUID add(UserReq req, UUID runId){
//        User entity = userMapper.dtoToEntity(req);
        User entity = null;
        if( req!= null)
            entity = new User();
        entity.setLogin(req.getLogin());
        entity.setPassword(req.getPassword());

        String login = entity.getLogin();
        String password = entity.getPassword();

        SoftReference<byte[]> bytes = map.get(login);
        if(bytes != null && bytes.get() != null){
            userExist(runId, login);
        } else {
            Optional<User> byLogin = userRepository.findByLogin(login);
            if (byLogin.isPresent()) {

                getAndSaveHashedPassword(login, password);
                userExist(runId, login);
            }
        }

        User save = userRepository.save(entity);
        UUID uuid = null;
        if(save != null) {
            uuid = save.getId();
            getAndSaveHashedPassword(login, password);
        }
        logger.info(String.format("User add Done: runId: '%s', aggregateId: '%s'", runId, uuid));
        return uuid;
    }

    private void getAndSaveHashedPassword(String login, String password) {
        byte[] digest = PasswordHash.createPasswordHash(password,hash_alg);
        map.put(login, new SoftReference<>(digest));
    }


    private void userExist(UUID runId, String login) {
        String message = String.format("User add: runId: '%s', User with login: '%s' exist.", runId, login);
        logger.warn(message);
        throw new ServiceEntityExistRuntimeException(message);
    }
}
