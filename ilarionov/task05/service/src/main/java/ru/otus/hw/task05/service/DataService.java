package ru.otus.hw.task05.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.task05.core.CoreDataStorage;
import ru.otus.hw.task05.core.entity.CoreEntity;
import ru.otus.hw.task05.provider.DataProvider;
import ru.otus.hw.task05.service.dto.ServiceDto;

@Service
public class DataService {

    private final DataProvider dataProvider;

    private final CoreDataStorage storage;

    public DataService(DataProvider dataProvider, CoreDataStorage storage) {
        this.dataProvider = dataProvider;
        this.storage = storage;
    }

    public ServiceDto put(String userText) {
        String randomText = dataProvider.generateAdditionalText();
        var coreEntity = new CoreEntity(userText, randomText);
        coreEntity = storage.put(coreEntity);
        return new ServiceDto(coreEntity.getId(), coreEntity.getUserText(), coreEntity.getRandomText());
    }

    public ServiceDto get(long id) {
        var coreEntity = storage.get(id);
        if (coreEntity == null) {
            throw new IllegalArgumentException("entity not found");
        }
        return new ServiceDto(coreEntity.getId(), coreEntity.getUserText(), coreEntity.getRandomText());
    }
}
