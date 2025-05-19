package dev.korolz;

import java.util.Map;

public class DataService {

    private final InMemoryDatabase database = new InMemoryDatabase();
    private final DataProvider provider;

    public DataService(DataProvider provider) {
        this.provider = provider;
    }

    public long saveData() {
        String data = provider.fetchData();
        return database.save(data);
    }

    public Map<Long, String> getAll() {
        return database.getDatabase();
    }
}
