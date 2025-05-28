package org.example.task12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Task12Application {

	public static void main(String[] args) {
		SpringApplication.run(Task12Application.class, args);
	}

}
