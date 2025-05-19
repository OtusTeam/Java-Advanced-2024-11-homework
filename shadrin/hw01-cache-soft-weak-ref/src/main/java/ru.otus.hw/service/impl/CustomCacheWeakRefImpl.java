package ru.otus.hw.service.impl;

import ru.otus.hw.domain.FileContent;
import ru.otus.hw.service.CustomCache;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomCacheWeakRefImpl implements CustomCache {

    private final Map<String, WeakReference<FileContent>> cache;

    public CustomCacheWeakRefImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public FileContent getContent(String file) {
        WeakReference<FileContent> reference = cache.get(file);
        if (reference == null || reference.get() == null) {
            return null;
        }
        System.out.println("Reading data from a cache");
        return reference.get();
    }

    @Override
    public void addContent(String file, FileContent content) {
        WeakReference<FileContent> reference = new WeakReference<>(content);
        cache.put(file, reference);
    }

    @Override
    public void clearCache() {
        cache.clear();
    }
}