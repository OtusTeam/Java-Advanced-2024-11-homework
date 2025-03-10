package ru.otus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MappedByteBufferStorageImplTest {
    private static final String TEST_FILE = "mapped_buffer_file.txt";
    private static final String NON_EXISTENT_FILE = "non_existent_file.txt";
    private static final String TEST_CONTENT = "Test file content";

    private MappedByteBufferStorageImpl storage;

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(Path.of(TEST_FILE), TEST_CONTENT);
        storage = new MappedByteBufferStorageImpl(512);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void shouldLoadFile() {
        assertDoesNotThrow(() -> storage.loadFile(TEST_FILE));
    }

    @Test
    void shouldReadFile() throws IOException {
        storage.loadFile(TEST_FILE);
        String result = storage.readData();
        assertTrue(result.contains(TEST_CONTENT));
    }

    @Test
    void shouldThrowNotFoundError() {
        IOException exception = assertThrows(IOException.class, () -> storage.loadFile(NON_EXISTENT_FILE));
        assertTrue(exception.getMessage().contains("File not found"));
    }

    @Test
    void shouldThrowReadError() {
        ByteBufferStorageImpl storage = new ByteBufferStorageImpl(0);
        RuntimeException exception = assertThrows(RuntimeException.class, storage::readData);
        assertTrue(exception.getMessage().contains("Buffer is empty"));
    }
}