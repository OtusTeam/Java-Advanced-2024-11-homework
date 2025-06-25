package dev.sivakova;

import dev.sivakova.core.cache.FileCache;
import dev.sivakova.core.model.User;
import dev.sivakova.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public Application() {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, FileCache userFileCache) {
        return (args) -> {
            User user = new User("John", "secret");
            userRepository.save(user);

            while(true) {
                userFileCache.get(user);
            }
        };
    }
}
