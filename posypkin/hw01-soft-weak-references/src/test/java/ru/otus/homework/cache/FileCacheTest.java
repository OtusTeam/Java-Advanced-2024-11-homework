package ru.otus.homework.cache;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileCacheTest {

    @Test
    @DisplayName("Проверяем что данные кэшируются")
    public void getCacheValueTest() {
        FileCache fileCache = new FileCache("src/test/resources/files/");

        // Указываем путь к папке и файлу
        String folderName = "src/test/resources/files";
        String fileName = "Address.txt";
        String expected = "Address";

        // Создаем объект File для папки и файла
        File folder = new File(folderName);
        File file = new File(folder, fileName);

        try {
            var result = fileCache.get("Address.txt");
            Assertions.assertEquals(expected, result);
            // Изменяем содержимое файла
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Updated content in the file.");
            }

            var cacheResult= fileCache.get("Address.txt");
            Assertions.assertEquals(expected, cacheResult);
            // Изменяем содержимое файла
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Address");
            }
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверяем что данные кэшируются")
    public void getCacheValueTest2() {
        FileCache fileCache = new FileCache("src/test/resources/files/");

        // Указываем путь к папке и файлу
        String folderName = "src/test/resources/files";
        String fileName = "Names.txt";
        String expected = "Some text";

        // Создаем объект File для папки и файла
        File folder = new File(folderName);
        File file = new File(folder, fileName);

        try {
            var result = fileCache.get("Names.txt");
            Assertions.assertEquals(expected, result);
            // Изменяем содержимое файла
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Updated content in the file.");
            }

            var cacheResult= fileCache.get("Names.txt");
            Assertions.assertEquals(expected, cacheResult);
            // Изменяем содержимое файла
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Names");
            }
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}