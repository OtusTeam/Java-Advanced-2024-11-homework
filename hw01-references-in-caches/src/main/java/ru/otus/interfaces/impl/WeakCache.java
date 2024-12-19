package ru.otus.interfaces.impl;

import ru.otus.interfaces.Cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeakCache<K> implements Cache<K, String> {

    private final Map<K, WeakReference<String>> cache = new ConcurrentHashMap<>();
    private final ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();

    @Override
    public String get(K key) {
        cleanUp();
        WeakReference<String> ref = cache.get(key);
        return (ref != null) ? ref.get() : null;
    }

    @Override
    public void load(K key, String value) {
        cleanUp();
        cache.put(key, new WeakReference<>(value, referenceQueue));
    }

    private void cleanUp() {
        WeakReference<? extends String> ref;
        while ((ref = (WeakReference<? extends String>) referenceQueue.poll()) != null) {
            WeakReference<? extends String> finalRef = ref;
            cache.values().removeIf(existingRef -> existingRef == finalRef);
        }
    }
}
