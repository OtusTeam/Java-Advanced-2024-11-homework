package org.example.task15.serviceExceptions;

public class ServiceEntityExistRuntimeException extends RuntimeException {
    public ServiceEntityExistRuntimeException(String message) {
        super(message);
    }
}
