package ru.otus;

import java.io.IOException;

public interface OffHeapStorage {
    void loadFile(String filePath) throws IOException;
    String readData();
}
