package ru.otus.hw.service.impl;

import ru.otus.hw.domain.FileContent;
import ru.otus.hw.service.CustomCache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCacheSoftRefImpl implements CustomCache {

    private final Map<String, SoftReference<FileContent>> cache;

    public CustomCacheSoftRefImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public FileContent getContent(String file) {
        SoftReference<FileContent> reference = cache.get(file);
        if (reference == null || reference.get() == null) {
            return null;
        }
        System.out.println("Reading data from a cache");
        return reference.get();
    }

    @Override
    public void addContent(String file, FileContent content) {
        SoftReference<FileContent> reference = new SoftReference<>(content);
        cache.put(file, reference);
    }

    @Override
    public void clearCache() {
        cache.clear();
    }
}