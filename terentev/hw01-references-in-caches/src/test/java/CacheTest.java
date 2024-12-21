import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.domain.Emulator;
import ru.otus.interfaces.impl.SoftCache;
import ru.otus.interfaces.impl.WeakCache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    private static final String TEST_DIRECTORY = "test_cache_directory";
    private static final String TEST_FILE_NAME = "test_file.txt";
    private static final String TEST_FILE_CONTENT = "This is a test file.";

    @BeforeEach
    void setUp() throws IOException {
        Path testDir = Paths.get(TEST_DIRECTORY);
        if (!Files.exists(testDir)) {
            Files.createDirectory(testDir);
        }

        Path testFile = testDir.resolve(TEST_FILE_NAME);
        Files.writeString(testFile, TEST_FILE_CONTENT);
    }

    @Test
    void testSoftCache() {
        SoftCache<String> cache = new SoftCache<>();

        cache.load(TEST_FILE_NAME, TEST_FILE_CONTENT);

        String cachedContent = cache.get(TEST_FILE_NAME);
        assertEquals(TEST_FILE_CONTENT, cachedContent, "SoftCache should return the stored content.");

        cache.load("large", new String(new byte[10_000_000]));
        System.gc();
        assertNotNull(cache.get(TEST_FILE_NAME), "Content in SoftCache might still be available after GC.");
    }

    @Test
    void testWeakCache() {
        WeakCache<String> cache = new WeakCache<>();

        cache.load(TEST_FILE_NAME, TEST_FILE_CONTENT);

        assertEquals(TEST_FILE_CONTENT, cache.get(TEST_FILE_NAME), "WeakCache should return the stored content.");

        cache.load(TEST_FILE_NAME, null); // Simulate content removal
        System.gc();

        assertNull(cache.get(TEST_FILE_NAME), "Content in WeakCache should be cleared after GC.");
    }

    @Test
    void testEmulatorSetDirectory() {
        Emulator emulator = new Emulator(new SoftCache<>());

        emulator.setCacheDirectory(TEST_DIRECTORY);
        assertDoesNotThrow(() -> emulator.setCacheDirectory(TEST_DIRECTORY), "Setting a valid directory should not throw.");

        String invalidDirectory = UUID.randomUUID().toString();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> emulator.setCacheDirectory(invalidDirectory));
        assertTrue(e.getMessage().contains("Invalid directory path"), "Setting an invalid directory should throw an exception.");
    }

    @Test
    void testEmulatorLoadAndGetFile() {
        Emulator emulator = new Emulator(new SoftCache<>());
        emulator.setCacheDirectory(TEST_DIRECTORY);

        emulator.loadFileIntoCache(TEST_FILE_NAME);
        String content = emulator.cache.get(TEST_FILE_NAME);
        assertEquals(TEST_FILE_CONTENT, content, "File content should be loaded into the cache.");
    }

    @Test
    void testEmulatorLoadFileNotExists() {
        Emulator emulator = new Emulator(new SoftCache<>());
        emulator.setCacheDirectory(TEST_DIRECTORY);

        String missingFile = "missing.txt";
        emulator.loadFileIntoCache(missingFile);
        String content = emulator.cache.get(missingFile);
        assertNull(content, "Nonexistent file should not be loaded into the cache.");
    }
}
