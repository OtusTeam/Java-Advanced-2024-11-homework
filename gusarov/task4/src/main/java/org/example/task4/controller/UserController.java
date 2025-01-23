package org.example.task4.controller;

import org.example.task4.controller.api.UserApi;
import org.example.task4.models.ResultWithId;
import org.example.task4.models.UserReq;
import org.example.task4.service.UserService;
import org.example.task4.serviceExceptions.ServiceEntityExistRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class UserController implements UserApi {

    private final UserService service;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<ResultWithId> create(UserReq req) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("USER_CREATE: runId: '%s', req: '%s'", runId, req));
            return new ResponseEntity<>(new ResultWithId(service.add(req, runId)), HttpStatus.resolve(201));
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
}
