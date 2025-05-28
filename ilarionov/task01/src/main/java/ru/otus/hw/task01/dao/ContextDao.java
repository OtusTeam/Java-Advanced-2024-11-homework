package ru.otus.hw.task01.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@Getter
@Setter
public class ContextDao {

    private static final String NOT_SET_CATALOG = "<path is not set, run init>";

    private Path catalog = null;

    public String getCatalogInfo() {
        if (catalog == null) {
            return NOT_SET_CATALOG;
        }
        return catalog.toString();
    }
}
