package ru.otus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class StorageUtils {

    public static Path validateFilePath(String filename) {
        try {
            Path path = Paths.get(Objects.requireNonNull(
                    StorageUtils.class.getClassLoader().getResource(filename)).toURI());
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Файл не найден: " + filename);
            }
            return path;
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке файла: " + e.getMessage());
            return null;
        }
    }
}