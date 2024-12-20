package cache;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheImpl implements Cache {

    private final Map<String, Reference<FileContent>> cache = new HashMap<>();
    private Path cacheDirectory;
    private String referenceType = "soft"; // "soft" - SoftReference, "weak" - WeakReference

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

    @Override
    public void setReferenceType(String referenceType) {
        if (!"soft".equalsIgnoreCase(referenceType) &&
                !"weak".equalsIgnoreCase(referenceType)) {
            throw new RuntimeException("Reference type must be 'soft' or 'weak'");
        }
        this.referenceType = referenceType;
    }

    private void loadFile(String fileName) throws IOException {

        var filePath = cacheDirectory.resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found");
        }
        var content = Files.readString(filePath);

        if ("soft".equalsIgnoreCase(referenceType)) {
            cache.put(fileName, new SoftReference<>(new FileContent(content)));
        } else {
            cache.put(fileName, new WeakReference<>(new FileContent(content)));
        }
    }
}
