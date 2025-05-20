package dev.korolz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @TempDir
    Path tempDir;

    private Cache<String, String> weakCache;
    private Cache<String, String> softCache;

    @BeforeEach
    void setUp() {
        weakCache = CacheFactory.createCache(CacheFactory.CacheType.WEAK);
        softCache = CacheFactory.createCache(CacheFactory.CacheType.SOFT);

        weakCache.setCacheDirectory(tempDir.toString());
        softCache.setCacheDirectory(tempDir.toString());
    }

    @Test
    void testLoadAndGet() throws IOException {
        // Создаем тестовый файл
        String fileName = "test.txt";
        String content = "This is a test content";
        Files.writeString(tempDir.resolve(fileName), content);

        // Тестируем WeakReferenceCache
        assertNull(((AbstractCache<String, String>)weakCache).getFromCache(fileName));
        weakCache.load(fileName);
        assertNotNull(((AbstractCache<String, String>)weakCache).getFromCache(fileName));
        assertEquals(content, weakCache.get(fileName));

        // Тестируем SoftReferenceCache
        assertNull(((AbstractCache<String, String>)softCache).getFromCache(fileName));
        softCache.load(fileName);
        assertNotNull(((AbstractCache<String, String>)softCache).getFromCache(fileName));
        assertEquals(content, softCache.get(fileName));
    }

    @Test
    void testNonExistentFile() {
        String nonExistentFileName = "nonexistent.txt";

        assertThrows(IOException.class, () -> weakCache.load(nonExistentFileName));
        assertThrows(IOException.class, () -> softCache.load(nonExistentFileName));
    }

    @Test
    void testNoCacheDirectory() {
        Cache<String, String> noDirectoryCache = CacheFactory.createCache(CacheFactory.CacheType.SOFT);

        assertThrows(IllegalStateException.class, () ->
                noDirectoryCache.load("any.txt")
        );
    }
}