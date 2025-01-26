package ru.otus.service;


import ru.otus.core.Entity;
import ru.otus.core.Repository;
import ru.otus.provider.Provider;

import java.util.List;

public class Service {
    public static void saveData() {
        Repository.save(Provider.provideData());
    }

    public static List<Entity> getAllData() {
        return Repository.findAll();
    }
}
