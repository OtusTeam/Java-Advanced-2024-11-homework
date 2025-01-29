package ru.otus.hw.task05.provider;

import org.springframework.stereotype.Component;
import ru.otus.hw.task05.core.CoreDataStorage;

@Component
public class DataProvider {

    private final CoreDataStorage coreDataStorage;

    public DataProvider(CoreDataStorage coreDataStorage) {
        this.coreDataStorage = coreDataStorage;
    }

    public long put(String text) {
        return coreDataStorage.put(text);
    }

    public String get(long id) {
        return coreDataStorage.get(id);
    }
}
