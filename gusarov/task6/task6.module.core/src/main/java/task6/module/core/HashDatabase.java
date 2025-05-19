package task6.module.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashDatabase {
    private final Map<UUID, String> map = new HashMap<>();

    public synchronized UUID save(String data) {
        UUID id = UUID.randomUUID();
        map.put(id, data);
        return id;
    }
}
