package ru.otus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ByteBufferStorageImplTest {
    private static final String TEST_FILE = "bytebuffer_file.txt";
    private static final String EMPTY_FILE = "empty_file.txt";
    private static final String NON_EXISTENT_FILE = "non_existent_file.txt";
    private static final String TEST_CONTENT = "Test file content";

    private ByteBufferStorageImpl storage;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(Path.of(TEST_FILE), TEST_CONTENT);
        Files.createFile(Path.of(EMPTY_FILE));
        storage = new ByteBufferStorageImpl(512);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
        Files.deleteIfExists(Path.of(EMPTY_FILE));
    }

    @Test
    void shouldLoadFile() {
        assertDoesNotThrow(() -> storage.loadFile(TEST_FILE));
    }

    @Test
    void shouldReadFile() throws IOException {
        storage.loadFile(TEST_FILE);
        String result = storage.readData();
        assertEquals(TEST_CONTENT, result, "File content should match");
    }

    @Test
    void shouldThrowNotFoundError() {
        IOException exception = assertThrows(IOException.class, () -> storage.loadFile(NON_EXISTENT_FILE));
        assertTrue(exception.getMessage().contains("File not found"));
    }

    @Test
    void shouldThrowEmptyError() {
        IOException exception = assertThrows(IOException.class, () -> storage.loadFile(EMPTY_FILE));
        assertTrue(exception.getMessage().contains("File is empty"));
    }

    @Test
    void shouldThrowReadError() {
        ByteBufferStorageImpl storage = new ByteBufferStorageImpl(0);
        RuntimeException exception = assertThrows(RuntimeException.class, storage::readData);
        assertTrue(exception.getMessage().contains("Buffer is empty"));
    }
}
