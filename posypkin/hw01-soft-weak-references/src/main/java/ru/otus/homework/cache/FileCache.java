package ru.otus.homework.cache;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileCache {

    private final Path cacheDirectoryPath;
    private final Map<String, WeakReference<String>> cacheYoung = new HashMap<>();
    private final Map<String, SoftReference<String>> cacheOld = new HashMap<>();
    private final Map<String, Integer> filesAge = new HashMap<>();
    private int ageLimit = 3;

    public FileCache(String first, String... cacheDirectoryPath) {
        this.cacheDirectoryPath = Paths.get(ensureTrailingSlash(first), cacheDirectoryPath);
    }

    public FileCache(int ageLimit, String first, String... cacheDirectoryPath) {
        this.cacheDirectoryPath = Paths.get(ensureTrailingSlash(first), cacheDirectoryPath);
        this.ageLimit = ageLimit;
    }

    private String ensureTrailingSlash(String path) {
        return path.endsWith("/") ? path : path + "/";
    }

    public String get(String fileName) {
        System.out.println("Get the file with fileName = " + fileName + " from a cache");
        var age = ageing(fileName);
        Reference<String> ref = cacheYoung.get(fileName);
        if (isNull(ref)) {
            ref = cacheOld.get(fileName);
            if (isNull(ref)) {
                System.out.println("The file with fileName = " + fileName + " was not found in a cache");
                var value = getFileFromCacheDirectory(fileName);
                ref = putByAge(age, fileName, value);
            } else {
                System.out.println("The file with fileName = " + fileName + " was received from the OldCache");
            }
        } else {
            System.out.println("The file with fileName = " + fileName + " was received from the YongCache");
        }
        return ref.get();
    }

    private boolean isNull(Reference<String> ref) {
        return ref == null || ref.get() == null;
    }

    private String getFileFromCacheDirectory(String fileName) {
        Path path = cacheDirectoryPath.resolve(fileName);
        try {
            return Files.readString(path);
        } catch (Exception ex) {
            throw new IllegalArgumentException("File not found. FilePath = " + path);
        }
    }

    public void put(String fileName) {
        System.out.println("The file with fileName = " + fileName + " put in a cache");
        var age = ageing(fileName);
        putByAge(age, fileName, getFileFromCacheDirectory(fileName));
    }

    private int ageing(String fileName) {
        return filesAge.compute(
                fileName,
                (key, oldValue) -> oldValue == null
                        ? 1
                        : oldValue + 1
        );
    }

    private Reference<String> putByAge(int age, String fileName, String value) {
        Reference<String> ref;
        if (age >= ageLimit) {
            ref = new SoftReference<>(value);
            cacheOld.put(fileName, (SoftReference<String>) ref);
        } else {
            ref = new WeakReference<>(value);
            cacheYoung.put(fileName, (WeakReference<String>) ref);
        }
        return ref;
    }
}
