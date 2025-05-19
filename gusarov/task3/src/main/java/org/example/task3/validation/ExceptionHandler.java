package org.example.task3.validation;

import org.example.task3.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(JsonValidationException.class)
    public ResponseEntity<Error> handle(JsonValidationException jsonValidationException, WebRequest webRequest) {
        Error validationError = new Error();
        validationError.setTimestamp(ZonedDateTime.now(Clock.systemUTC()));
        validationError.setStatus(400);
        validationError.setError("Bad request");
        validationError.setMessage(jsonValidationException.getMessage());
        validationError.setPath(((ServletWebRequest) webRequest).getRequest()
                .getServletPath());
        return new ResponseEntity<>(validationError, HttpStatusCode.valueOf(400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Error> responseStatusExceptionHandler(Exception ex, WebRequest request) {
        ResponseStatusException exception = (ResponseStatusException) ex;
        HttpStatus status = HttpStatus.resolve(exception.getStatusCode().value());

        Error error = new Error();
        error.setTimestamp(ZonedDateTime.now(Clock.systemUTC()));
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(status.value()));
    }
}

