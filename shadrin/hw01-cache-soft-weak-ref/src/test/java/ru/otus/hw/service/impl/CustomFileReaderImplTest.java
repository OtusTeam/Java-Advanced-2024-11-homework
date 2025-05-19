package ru.otus.hw.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.FileContent;
import ru.otus.hw.domain.TypeOfCaching;
import ru.otus.hw.service.CustomFileReader;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для чтения данных из файла с использованием кэша на основе Weak и Soft ссылок")
class CustomFileReaderImplTest {

    private CustomFileReader fileReader;

    private String fileName;

    @BeforeEach
    void setUp() {
        fileReader = new CustomFileReaderImpl();
        fileName = "Names.txt";
    }

    @Test
    @DisplayName("должен при повторном запросе к сервису загружать объект из кэша на основе Weak ссылок")
    void shouldReturnContentFromWeakRefCache() {
        fileReader.configureCaching(TypeOfCaching.WEAK_REF);

        FileContent contentFromFile = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentFromFile).isNotNull();

        FileContent contentFromCache = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentFromCache).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(contentFromFile);
    }

    @Test
    @DisplayName("должен после отработки GC при повторном запросе к сервису снова читать данные из файла, " +
            "в качестве признака, что объект был прочитан из файла используется dateTimeOfDataReading")
    void shouldClearWeakRefCache() {
        fileReader.configureCaching(TypeOfCaching.WEAK_REF);

        FileContent contentFirstRequest = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentFirstRequest).isNotNull();

        System.gc();

        FileContent contentSecondRequest = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentSecondRequest).isNotNull()
                .matches(content -> content.dateTimeOfDataReading().isAfter(contentFirstRequest.dateTimeOfDataReading()));
    }

    @Test
    @DisplayName("должен при повторном запросе к сервису загружать объект из кэша на основе Soft ссылок")
    void shouldReturnContentFromSoftRefCache() {
        fileReader.configureCaching(TypeOfCaching.SOFT_REF);

        FileContent contentFromFile = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentFromFile).isNotNull();

        FileContent contentFromCache = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentFromCache).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(contentFromFile);
    }

    @Test
    @DisplayName("должен после создания нагрузки на память при повторном запросе к сервису снова читать данные из файла, " +
            "в качестве признака, что объект был прочитан из файла используется dateTimeOfDataReading")
    void shouldClearSoftRefCache() {
        fileReader.configureCaching(TypeOfCaching.SOFT_REF);

        FileContent contentFirstRequest = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentFirstRequest).isNotNull();

        createsLoadOnMemory();

        FileContent contentSecondRequest = createCloneOfObject(fileReader.getContent(fileName));
        assertThat(contentSecondRequest).isNotNull()
                .matches(content -> content.dateTimeOfDataReading().isAfter(contentFirstRequest.dateTimeOfDataReading()));
    }

    private FileContent createCloneOfObject(FileContent fileContent) {
        return new FileContent(fileContent.dateTimeOfDataReading(), fileContent.content());
    }

    private void createsLoadOnMemory() {
        try {
            List<byte[]> memoryConsumers = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                memoryConsumers.add(new byte[10_000_000]);
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError: " + e);
        }
    }
}