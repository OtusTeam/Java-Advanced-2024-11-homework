package ru.otus.nio;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MappedByteBufferStoreTest {

    private static final String FILE_PATH = "testfile.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Создание пустого файла для теста
        Files.deleteIfExists(Paths.get(FILE_PATH));
        Files.createFile(Paths.get(FILE_PATH));
    }

    @Test
    void testWriteAndReadFromBuffer() throws IOException {
        MappedByteBufferStore store = new MappedByteBufferStore(FILE_PATH);
        String dataToWrite = "Hello, world!";
        store.writeToBuffer(dataToWrite);
        String readData = store.readFromBuffer();
        System.out.println("Читаем из буфера: " + readData);
        assertEquals(dataToWrite, readData);

        String content = Files.readString(Paths.get(FILE_PATH));
        System.out.println("Читаем из файла: " + content.trim());
        assertEquals(readData, content.trim());

        store.close();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(FILE_PATH));
    }
}




