package org.example.task18.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.example.task18.api.CircuitBreakerLimiter;
import org.example.task18.controller.api.UserApi;
import org.example.task18.models.ResultWithId;
import org.example.task18.models.UserReq;
import org.example.task18.service.UserService;
import org.example.task18.serviceExceptions.ServiceEntityExistRuntimeException;
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
    private final CircuitBreakerLimiter circuitBreakerLimiter;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service, CircuitBreakerLimiter circuitBreakerLimiter) {
        this.service = service;
        this.circuitBreakerLimiter = circuitBreakerLimiter;
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

    @Override
    public ResponseEntity<Integer> year(UUID id) {
        UUID runId = UUID.randomUUID();
        try {
            logger.info(String.format("YEAR: runId: '%s', userId: '%s'", runId, id));
            return new ResponseEntity<>(circuitBreakerLimiter.year(id, runId), HttpStatus.resolve(200));
        } catch (ServiceEntityExistRuntimeException e) {
            String err = String.format("year: ServiceEntityExistRuntimeException runId: '%s', userId: '%s', error: '%s'", runId, id, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(422), String.format("Details: '%s' ", e.getMessage()));
        } catch (CallNotPermittedException  e) {
            String err = String.format("year: CallNotPermittedException runId: '%s', userId: '%s', error: '%s'", runId, id, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(500), String.format("Details: '%s' ", e.getMessage()));
        } catch (RequestNotPermitted e) {
            String err = String.format("year: RequestNotPermitted runId: '%s', userId: '%s', error: '%s'", runId, id, e);
            logger.warn(err);
            throw new ResponseStatusException(HttpStatus.resolve(500), String.format("Details: '%s' ", e.getMessage()));
        } catch (Exception e) {
            String err = String.format("year: Error runId: '%s', userId: '%s', error: '%s'", runId, id, e);
            logger.error(err);
            throw e;
        }
    }
}
