package org.example.task7.serviceExceptions;

public class ServiceEntityExistRuntimeException extends RuntimeException {
    public ServiceEntityExistRuntimeException(String message) {
        super(message);
    }
}
