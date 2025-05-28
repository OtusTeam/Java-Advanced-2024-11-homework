package dev.korolz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractCache<K, V> implements Cache<K, V> {
    protected String directoryPath;

    @Override
    public void setCacheDirectory(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public V get(K key) throws IOException {
        V value = getFromCache(key);
        if (value == null) {
            load(key);
            value = getFromCache(key);
        }
        return value;
    }

    protected abstract V getFromCache(K key);

    protected String readFileContent(String fileName) throws IOException {
        if (directoryPath == null || directoryPath.isEmpty()) {
            throw new IllegalStateException("Директория кэша не установлена");
        }
        Path filePath = Path.of(directoryPath, fileName);
        return Files.readString(filePath);
    }
}