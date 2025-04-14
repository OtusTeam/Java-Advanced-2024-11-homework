package dev.korolz;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class WeakReferenceCache extends AbstractCache<String, String> {
    private final Map<String, WeakReference<String>> cache = new HashMap<>();

    @Override
    protected String getFromCache(String key) {
        WeakReference<String> reference = cache.get(key);
        return reference != null ? reference.get() : null;
    }

    @Override
    public void load(String key) throws IOException {
        String content = readFileContent(key);
        cache.put(key, new WeakReference<>(content));
    }
}