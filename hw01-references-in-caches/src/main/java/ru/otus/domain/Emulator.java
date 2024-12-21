package ru.otus.domain;

import ru.otus.interfaces.Cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Emulator {

    private Path cacheDirectory;
    public final Cache<String, String> cache;

    public Emulator(Cache<String, String> cache) {
        this.cache = cache;
    }

    public void setCacheDirectory(String directoryPath) {
        if (directoryPath == null || directoryPath.isEmpty()) {
            throw new IllegalArgumentException("Directory path cannot be null or empty.");
        }

        Path path = Paths.get(directoryPath);

        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            throw new RuntimeException("Directory cannot be created");
        }

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Invalid directory path: " + path);
        }

        cacheDirectory = path;
        System.out.println("Cache directory set to: " + cacheDirectory);
    }

    public void loadFileIntoCache(String fileName) {
        if (cacheDirectory == null) {
            throw new IllegalStateException("Cache directory is not set.");
        }

        Path filePath = cacheDirectory.resolve(fileName);
        if (Files.exists(filePath)) {
            try {
                String content = Files.readString(filePath);
                cache.load(fileName, content);
                System.out.println("File loaded into cache: " + fileName);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.err.println("File does not exist: " + fileName);
        }
    }

    public void getFileFromCache(String fileName) {
        String content = cache.get(fileName);

        if (content == null) {
            System.out.println("File not found in cache. Attempting to load from directory...");
            loadFileIntoCache(fileName);
            content = cache.get(fileName);
        }

        if (content != null) {
            System.out.println("File content:");
            System.out.println(content);
        } else {
            System.out.println("File not found in cache: " + fileName);
        }
    }
}