package ru.otus.hw.task01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.task01.dao.ApplicationCache;
import ru.otus.hw.task01.dao.ContextDao;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final ContextDao contextDao;

    private final ApplicationCache applicationCache;

    public String buildCurrentStatus() {
        var currentPath = contextDao.getCatalogInfo();
        var cache = applicationCache.getCache();
        var cacheState = new HashMap<String, List<String>>();
        cache.forEach((key, value) -> cacheState.put(key, value.get()));
        return "Current path: " +
                currentPath + "\n" +
                "Current cache: " +
                cacheState + "\n";
    }
}
