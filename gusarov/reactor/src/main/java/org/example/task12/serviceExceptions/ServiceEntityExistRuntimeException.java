package org.example.task12.serviceExceptions;

public class ServiceEntityExistRuntimeException extends RuntimeException {
    public ServiceEntityExistRuntimeException(String message) {
        super(message);
    }
}
