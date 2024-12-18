package cache;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CacheImplTest {
    private static CacheImpl fileCache;
    private static Path testDirectory;
    private static final String testDirectoryName = "testDirectory";

    @BeforeAll
    static void setUp() throws IOException {
        fileCache = new CacheImpl();
        testDirectory = Files.createTempDirectory(testDirectoryName);
        Files.writeString(testDirectory.resolve("testFile.txt"), "Test content");

    }

    @Test
    void shouldSetCorrectDirectory() {
        fileCache.setCacheDirectory(testDirectory.toString());
    }

    @Test
    void shouldThrowAnErrorForInvalidDirectory() {
        var exception =
                assertThrows(RuntimeException.class,
                        () -> fileCache.setCacheDirectory("invalidDirectory"));
        assertEquals("Provided directory doesn't exist", exception.getMessage());
    }

    @Test
    void shouldLoadCorrectFile() throws IOException {
        fileCache.setCacheDirectory(testDirectory.toString());
        fileCache.loadFileIntoCache("testFile.txt");
        assertNotNull(fileCache.getFileContent("testFile.txt"));
    }

    @Test
    void shouldThrowAnErrorForInvalidFile() {
        fileCache.setCacheDirectory(testDirectory.toString());
        var exception =
                assertThrows(RuntimeException.class,
                        () -> fileCache.loadFileIntoCache("invalidFile.txt"));
        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void getFileContentFromCache() throws IOException {
        fileCache.setCacheDirectory(testDirectory.toString());
        fileCache.loadFileIntoCache("testFile.txt");
        String content = fileCache.getFileContent("testFile.txt");
        assertEquals("Test content", content);
    }

    @Test
    void getFileContentMissedInCache() throws IOException {
        fileCache.setCacheDirectory(testDirectory.toString());
        String content = fileCache.getFileContent("testFile.txt");
        assertEquals("Test content", content);
    }
}
