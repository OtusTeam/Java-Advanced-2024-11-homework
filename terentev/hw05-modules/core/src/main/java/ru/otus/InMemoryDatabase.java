package ru.otus;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {

    private final Map<Long, String> database = new HashMap<>();
    private long idCounter = 1;

    public synchronized long save(String data) {
        long id = idCounter++;
        database.put(id, data);
        return id;
    }

    public Map<Long, String> getDatabase() {
        return database;
    }
}
