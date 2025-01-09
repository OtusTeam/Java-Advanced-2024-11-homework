package dev.sivakova.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCache extends SoftCache<String, String> {
    private Path directory;
    public void setDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException(path + " not exists or not a directory");
        }
        directory = path;
    }

    @Override
    public String load(String fileName) throws IOException {
        if (directory == null) {
            throw new IllegalStateException("File directory is not set");
        }
        Path filePath = directory.resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new IllegalStateException("File doesn't exist");
        }
        return Files.readString(filePath);
    }
}
