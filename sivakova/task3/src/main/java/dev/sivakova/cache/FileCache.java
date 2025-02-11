package dev.sivakova.cache;

import dev.sivakova.model.FileInMemory;
import dev.sivakova.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileCache {
    private final Map<UserId, FileInMemory> entries = new ConcurrentHashMap<>();

    public FileInMemory get(final User user) {
        final UserId userId = new UserId(user.getId());
        return entries.computeIfAbsent(userId, key ->
                new FileInMemory("file.bin", new byte[1024])
        );
    }

    private class UserId {
        private final long id;

        public UserId(final long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public int hashCode() {
            return Long.hashCode(id);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            UserId userId = (UserId) o;
            return id == userId.id;
        }
    }
}