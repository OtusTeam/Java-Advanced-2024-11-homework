package cache;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheImpl implements Cache {

    private final Map<String, SoftReference<FileContent>> cache = new HashMap<>();
    private Path cacheDirectory;

    @Override
    public void setCacheDirectory(String directory) {

        this.cacheDirectory = Paths.get(directory);
        if (!Files.isDirectory(cacheDirectory)) {
            throw new RuntimeException("Provided directory doesn't exist");
        }
    }

    @Override
    public void loadFileIntoCache(String fileName) throws IOException {
        loadFile(fileName);
    }

    @Override
    public String getFileContent(String fileName) throws IOException {

        var reference = cache.get(fileName);
        var content = reference != null ? reference.get() : null;
        if (content == null) {
            loadFile(fileName);
            content = cache.get(fileName).get();
        }
        return Objects.requireNonNull(content).content();
    }

    private void loadFile(String fileName) throws IOException {

        var filePath = cacheDirectory.resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found");
        }
        var content = Files.readString(filePath);
        cache.put(fileName, new SoftReference<>(new FileContent(content)));
    }
}
