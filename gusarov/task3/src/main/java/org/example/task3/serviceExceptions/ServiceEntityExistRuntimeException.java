package org.example.task3.serviceExceptions;

public class ServiceEntityExistRuntimeException extends RuntimeException {
    public ServiceEntityExistRuntimeException(String message) {
        super(message);
    }
}
