package ru.otus.hw.task01.dao;

import org.springframework.stereotype.Component;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationCache {

    private static final Map<String, Reference<List<String>>> CACHE = new HashMap<>();

    public void put(String fileName, Reference<List<String>> text) {
        CACHE.put(fileName, text);
    }

    public List<String> get(String fileName) {
        if (CACHE.containsKey(fileName)) {
            return CACHE.get(fileName).get();
        }
        return null;
    }

    public Map<String, Reference<List<String>>> getCache() {
        return CACHE;
    }
}
