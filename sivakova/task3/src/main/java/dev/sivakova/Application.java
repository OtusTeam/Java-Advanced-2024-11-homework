package dev.sivakova;

import dev.sivakova.cache.FileCache;
import dev.sivakova.model.FileInMemory;
import dev.sivakova.model.User;
import dev.sivakova.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, FileCache userFileCache) {
        return (args) -> {
            User user = new User("John",
                    "secret");
            userRepository.save(user);
            while (true) {
                final FileInMemory userFile = userFileCache.get(user);
            }
        };
    }
}