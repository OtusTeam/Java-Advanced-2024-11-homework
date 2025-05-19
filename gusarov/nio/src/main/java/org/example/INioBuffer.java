package org.example;

import java.io.IOException;

public interface INioBuffer {
    void loadData(String filePath, int bufferSize) throws IOException;
    String readData();
}
