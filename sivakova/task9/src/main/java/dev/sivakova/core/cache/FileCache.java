package dev.sivakova.core.cache;

import dev.sivakova.core.model.FileInMemory;
import dev.sivakova.core.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileCache {
    private final Map<UserId, FileInMemory> entries = new ConcurrentHashMap<>();

    public FileCache() {
    }

    public synchronized FileInMemory get(final User user) {
        UserId userId = new UserId(user.getId());
        return this.entries.computeIfAbsent(userId,
                (key) -> {
                    return new FileInMemory("file.bin", new byte[1024]);
                });
    }

    private class UserId {
        private final long id;

        public UserId(final long id) {
            this.id = id;
        }

        public long getId() {
            return this.id;
        }
    }
}
