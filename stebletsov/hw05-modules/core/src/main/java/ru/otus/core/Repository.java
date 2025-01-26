package ru.otus.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {
    private static final List<Entity> REPOSITORY = new ArrayList<>();
    private static int currentId = 1;

    public static void save(String data) {
        var entity = new Entity(currentId++, data);
        REPOSITORY.add(entity);
    }

    public static List<Entity> findAll() {
        return Collections.unmodifiableList(REPOSITORY);
    }
}
