package ru.otus;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MappedByteBufferStorageImpl implements OffHeapStorage {
    private MappedByteBuffer mappedByteBuffer;
    private final int size;

    public MappedByteBufferStorageImpl(int size) {
        this.size = size;
    }

    @Override
    public void loadFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, size);
        }
    }

    @Override
    public String readData() {
        if (!mappedByteBuffer.hasRemaining()) {
            throw new RuntimeException("Buffer is empty");
        }
        mappedByteBuffer.rewind();
        byte[] bytes = new byte[mappedByteBuffer.remaining()];
        mappedByteBuffer.get(bytes);
        return new String(bytes);
    }
}
