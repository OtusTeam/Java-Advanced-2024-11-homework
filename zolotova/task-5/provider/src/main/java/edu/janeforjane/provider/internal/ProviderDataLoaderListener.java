package edu.janeforjane.provider.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.janeforjane.provider.internal.entities.FairytaleCharacter;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;



public class ProviderDataLoaderListener implements ApplicationListener<ContextRefreshedEvent> {

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
        String fairytaleCharactersFilePath = "fairytale-characters.json";
        Resource resourceShrekCharacters = new ClassPathResource(fairytaleCharactersFilePath);

        List<FairytaleCharacter> characters = Arrays.asList(objectMapper.readValue(resourceShrekCharacters.getInputStream(), FairytaleCharacter[].class));
        FairytaleCharactersStorage.fillData(characters);
    }
}
