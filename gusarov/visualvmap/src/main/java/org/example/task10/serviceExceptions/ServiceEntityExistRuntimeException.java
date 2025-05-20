package org.example.task10.serviceExceptions;

public class ServiceEntityExistRuntimeException extends RuntimeException {
    public ServiceEntityExistRuntimeException(String message) {
        super(message);
    }
}
