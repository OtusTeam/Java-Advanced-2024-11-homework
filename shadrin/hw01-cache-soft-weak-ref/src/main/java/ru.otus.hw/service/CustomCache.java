package ru.otus.hw.service;

import ru.otus.hw.domain.FileContent;

public interface CustomCache {

    FileContent getContent(String file);

    void addContent(String file, FileContent content);

    void clearCache();
}