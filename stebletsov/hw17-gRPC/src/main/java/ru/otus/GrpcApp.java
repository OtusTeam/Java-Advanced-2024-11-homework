package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcApp {
    public static void main(String[] args) {
        SpringApplication.run(GrpcApp.class, args);
    }
}