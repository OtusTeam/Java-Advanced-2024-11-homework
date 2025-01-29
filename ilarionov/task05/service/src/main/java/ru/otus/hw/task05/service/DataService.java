package ru.otus.hw.task05.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.task05.provider.DataProvider;

@Service
public class DataService {

    private final DataProvider dataProvider;

    public DataService(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public long put(String text) {
        return dataProvider.put(text);
    }

    public String get(long id) {
        return dataProvider.get(id);
    }
}
