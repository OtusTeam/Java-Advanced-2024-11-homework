package edu.janeforjane.core.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.janeforjane.core.internal.entities.ShrekCharacterDB;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class CoreDataLoaderListener implements ApplicationListener<ContextRefreshedEvent> {


    //ApplicationListener позволяет реагировать на события Spring, такие, как завершение контекста
    //для выполнения задач после полной инициализации контекста
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)  {
        // Логика загрузки данных
        try {
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fairytaleCharactersFilePath = "shrek-characters.json";
        Resource resourceShrekCharacters = new ClassPathResource(fairytaleCharactersFilePath);

        List<ShrekCharacterDB> characters = Arrays.asList(objectMapper.readValue(resourceShrekCharacters.getInputStream(), ShrekCharacterDB[].class));
        ShrekCharactersStorage.fillData(characters);
    }
}
