package ru.otus.hw.task05.core;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CoreDataStorage {

    private final Map<Long, String> data = new HashMap<>();

    private final AtomicLong counter = new AtomicLong();

    public synchronized long put(String text) {
        var id = counter.incrementAndGet();
        data.put(id, text);
        return id;
    }

    public String get(long id) {
        return data.getOrDefault(id, "no value");
    }
}
