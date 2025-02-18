package ru.otus;

public class DataService {

    private final InMemoryDatabase database;
    private final DataProvider provider;

    public DataService(InMemoryDatabase database, DataProvider provider) {
        this.database = database;
        this.provider = provider;
    }

    public long saveData() {
        String data = provider.fetchData();
        return database.save(data);
    }
}
