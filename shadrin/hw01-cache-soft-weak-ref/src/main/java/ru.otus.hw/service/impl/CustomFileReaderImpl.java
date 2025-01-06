package ru.otus.hw.service.impl;

import ru.otus.hw.domain.FileContent;
import ru.otus.hw.domain.TypeOfCaching;
import ru.otus.hw.service.CustomCache;
import ru.otus.hw.service.CustomFileReader;

import java.io.*;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class CustomFileReaderImpl implements CustomFileReader {

    private CustomCache cache;

    @Override
    public FileContent getContent(String file) {
        if (cache == null) {
            System.out.println("You need to set the caching setting.");
            return null;
        }
        FileContent content = cache.getContent(file);
        if (content == null) {
            content  = readContentFromFile(file);
        }
        if (content == null) {
            return null;
        }
        cache.addContent(file, content);
        return content;
    }

    @Override
    public TypeOfCaching getCurrentTypeOfCaching() {
        if (cache instanceof CustomCacheSoftRefImpl) {
            return TypeOfCaching.SOFT_REF;
        } else if (cache instanceof CustomCacheWeakRefImpl) {
            return TypeOfCaching.WEAK_REF;
        }
        return null;
    }

    @Override
    public void configureCaching(TypeOfCaching typeOfCaching) {
        if (typeOfCaching == TypeOfCaching.SOFT_REF) {
            cache = new CustomCacheSoftRefImpl();
            System.out.println("The caching mode is set - SoftReference");
        } else if (typeOfCaching == TypeOfCaching.WEAK_REF) {
            cache = new CustomCacheWeakRefImpl();
            System.out.println("The caching mode is set - WeakReference");
        }
    }

    @Override
    public void clearCache() {
        if (cache == null) {
            System.out.println("The cache is not configured yet");
            return;
        }
        cache.clearCache();
        System.out.println("The cache was forcibly cleared");
    }

    private FileContent readContentFromFile(String file) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
        if (isNull(inputStream)) {
            System.out.println("File resource not found");
            return null;
        }
        System.out.println("Reading data from a file");
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(bufferedReader.readLine());
            }
            return new FileContent(LocalDateTime.now(), stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}