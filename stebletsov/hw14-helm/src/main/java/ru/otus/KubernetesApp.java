package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KubernetesApp {
    public static void main(String[] args) {
        SpringApplication.run(KubernetesApp.class, args);
    }
}