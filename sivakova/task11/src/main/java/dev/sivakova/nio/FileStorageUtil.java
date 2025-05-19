package dev.sivakova.nio;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileStorageUtil {
    private FileStorageUtil() {
    }

    public static void validatePath(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Path is null");
        }
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path does not exist");
        }
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Path is not a file");
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("Path is not readable");
        }
    }
}
