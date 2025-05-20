package ru.otus.hw.service;

import ru.otus.hw.domain.FileContent;
import ru.otus.hw.domain.TypeOfCaching;

public interface CustomFileReader {

    FileContent getContent(String file);

    TypeOfCaching getCurrentTypeOfCaching();

    void configureCaching(TypeOfCaching typeOfCaching);

    void clearCache();
}