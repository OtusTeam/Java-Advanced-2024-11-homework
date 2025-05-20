package org.example.task12.controller;

import org.example.task12.controller.api.UserApi;
import org.example.task12.models.UserReq;
import org.example.task12.persistence.entity.User;
import org.example.task12.service.UserService;
import org.example.task12.serviceExceptions.ServiceEntityExistRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController implements UserApi {

    private final UserService service;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @Override
    public Mono<User> create(UserReq req) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_CREATE: runId: '%s', req: '%s'", runId, req));
            return service.add(req, runId);
        } catch (ServiceEntityExistRuntimeException e) {
            String err = String.format("create: ServiceEntityExistRuntimeException runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(422), String.format("Details: '%s' ", e.getMessage()));
        } catch (Exception e) {
            String err = String.format("create: Error runId: '%s', req: '%s', error: '%s'", runId, req, e);
            logger.error(err);
            throw e;
        }
    }

    @Override
    public Flux<User> all() {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_ALL: runId: '%s'", runId));
            return service.all();
        } catch (Exception e) {
            String err = String.format("all: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
    }

    @Override
    public Mono<List<String>> names() {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_NAMES: runId: '%s'", runId));
            return service.names().collectList();
        } catch (Exception e) {
            String err = String.format("names: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
    }

    @Override
    public Flux<String> emails() {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_EMAILS: runId: '%s'", runId));
            return service.emails();
        } catch (Exception e) {
            String err = String.format("emails: Error runId: '%s', error: '%s'", runId, e);
            logger.error(err);
            throw e;
        }
    }
}
