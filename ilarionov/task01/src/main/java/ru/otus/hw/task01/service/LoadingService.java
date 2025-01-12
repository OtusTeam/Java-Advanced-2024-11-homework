package ru.otus.hw.task01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.task01.dao.ApplicationCache;
import ru.otus.hw.task01.dao.ContextDao;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadingService {

    private final WeakCacheService weakCacheService;

    private final SoftCacheService softCacheService;

    private final ApplicationCache applicationCache;

    private final ContextDao contextDao;

    public String load(String fileName, String type) {
        var cachedContent = applicationCache.getCacheForFileByName(fileName);
        if (cachedContent != null) {
            log.info("File {} loaded from cache", fileName);
            saveInCache(type, fileName, cachedContent);
            return String.join("\n", cachedContent);
        }
        log.info("Cache is empty, loading from filesystem");
        var filePath = contextDao.getCatalog().resolve(fileName);
        try {
            var text = Files.readAllLines(filePath);
            saveInCache(type, fileName, text);
            return String.join("\n", text);
        } catch (IOException e) {
            log.error("Error while reading file", e);
            return "Error while reading file, try again";
        }
    }

    private void saveInCache(String type, String fileName, List<String> text) {
        if (type == null) {
            return;
        }
        switch (type) {
            case "weak" -> weakCacheService.save(fileName, text);
            case "soft" -> softCacheService.save(fileName, text);
        }
    }
}
