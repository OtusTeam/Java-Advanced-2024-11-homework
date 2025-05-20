package ru.otus.hw.task01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.task01.dao.ApplicationCache;

import java.lang.ref.WeakReference;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeakCacheService {

    private final ApplicationCache applicationCache;

    public void save(String fileName, List<String> text) {
        applicationCache.put(fileName, new WeakReference<>(text));
    }
}
