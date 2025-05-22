package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MemoryDumpApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemoryDumpApplication.class, args);
    }
}