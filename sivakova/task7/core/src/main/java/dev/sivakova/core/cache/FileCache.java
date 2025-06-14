//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.sivakova.core.cache;

import dev.sivakova.core.model.FileInMemory;
import dev.sivakova.core.model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class FileCache {
    private final Map<UserId, FileInMemory> entries = new ConcurrentHashMap();

    public FileCache() {
    }

    public FileInMemory get(final User user) {
        UserId userId = new UserId(user.getId());
        return (FileInMemory)this.entries.computeIfAbsent(userId, (key) -> new FileInMemory("file.bin", new byte[1024]));
    }

    private class UserId {
        private final long id;

        public UserId(final long id) {
            this.id = id;
        }

        public long getId() {
            return this.id;
        }

        public int hashCode() {
            return Long.hashCode(this.id);
        }

        public boolean equals(Object o) {
            if (o != null && this.getClass() == o.getClass()) {
                UserId userId = (UserId)o;
                return this.id == userId.id;
            } else {
                return false;
            }
        }
    }
}
