package ru.otus.interfaces.impl;

import ru.otus.interfaces.Cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache<K> implements Cache<K, String> {

    private final Map<K, SoftReference<String>> cache = new ConcurrentHashMap<>();

    @Override
    public String get(K key) {
        SoftReference<String> reference = cache.get(key);
        return (reference != null) ? reference.get() : null;
    }

    @Override
    public void load(K key, String value) {
        cache.put(key, new SoftReference<>(value));
    }
}
