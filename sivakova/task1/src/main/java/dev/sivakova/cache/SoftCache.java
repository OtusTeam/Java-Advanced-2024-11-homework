package dev.sivakova.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class SoftCache<K, V> {
    private final Map<K, SoftValue<K, V>> cache = new HashMap<>();
    private final ReferenceQueue<V> queue = new ReferenceQueue<>();

    public abstract V load(K key) throws Exception;

    public final V get(K key) {
        SoftValue<K, V> softValue = cache.get(key);
        if (softValue != null) {
            V value = softValue.get();
            if (value != null) {
                return value;
            }
        }
        V loadedValue;
        try {
            loadedValue = load(key);
        } catch (Exception e) {
            throw new RuntimeException("Can't put value to cache: " + e.getMessage());
        }
        if (loadedValue == null) {
            return null;
        }
        put(key, loadedValue);
        return loadedValue;
    }

    public final void put(K key, V value) {
        removeNotReferencedEntries();
        SoftValue<K, V> softValue = new SoftValue<>(value, key, queue);
        cache.put(key, softValue);
    }

    private void removeNotReferencedEntries() {
        Reference value;
        while ((value = queue.poll()) != null) {
            cache.remove(((SoftValue<K, V>) value).key);
        }
    }

    private static class SoftValue<K, V> extends SoftReference<V> {
        private final K key;

        private SoftValue(V value, K key, ReferenceQueue<V> queue) {
            super(value, queue);
            this.key = key;
        }
    }
}
