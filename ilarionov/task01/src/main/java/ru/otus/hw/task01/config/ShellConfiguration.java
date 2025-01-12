package ru.otus.hw.task01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Availability;
import org.springframework.shell.AvailabilityProvider;
import ru.otus.hw.task01.dao.ContextDao;

@Configuration
public class ShellConfiguration {

    @Bean
    public AvailabilityProvider loadingFileCommandAvailabilityProvider(ContextDao contextDao) {
        return () -> contextDao.getCatalog() != null
                ? Availability.available()
                : Availability.unavailable("Set catalog path first");
    }
}
