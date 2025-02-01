package ru.otus.hw.task05.core;

import org.springframework.stereotype.Repository;
import ru.otus.hw.task05.core.entity.CoreEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CoreDataStorage {

    private final Map<Long, CoreEntity> data = new HashMap<>();

    private final AtomicLong counter = new AtomicLong();

    public synchronized CoreEntity put(CoreEntity coreEntity) {
        var id = counter.incrementAndGet();
        coreEntity.setId(id);
        data.put(id, coreEntity);
        return coreEntity;
    }

    public CoreEntity get(long id) {
        return data.getOrDefault(id, null);
    }
}
