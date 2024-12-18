package cache;

import java.io.IOException;

public interface Cache {
    void setCacheDirectory(String directory);
    void loadFileIntoCache(String fileName) throws IOException;
    String getFileContent(String fileName) throws IOException;
    void setReferenceType(String referenceType);
}
