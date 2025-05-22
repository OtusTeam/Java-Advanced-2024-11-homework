package dev.korolz;

import java.io.IOException;

public interface Cache<K, V> {
    void setCacheDirectory(String directoryPath);
    V get(K key) throws IOException;
    void load(K key) throws IOException;
}