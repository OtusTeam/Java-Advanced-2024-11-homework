package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoryDumpHomeworkApplication {

	//-Xmx32m -Xms32m
	public static void main(String[] args) {
		SpringApplication.run(MemoryDumpHomeworkApplication.class, args);
	}

}
