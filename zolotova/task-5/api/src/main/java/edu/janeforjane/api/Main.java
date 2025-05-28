package edu.janeforjane.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.janeforjane.api", "edu.janeforjane.service", "edu.janeforjane.entities", "edu.janeforjane.core", "edu.janeforjane.provider"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}