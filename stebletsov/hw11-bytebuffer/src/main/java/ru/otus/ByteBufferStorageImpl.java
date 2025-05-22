package ru.otus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.channels.FileChannel;

public class ByteBufferStorageImpl implements OffHeapStorage {
    private final ByteBuffer byteBuffer;

    public ByteBufferStorageImpl(int capacity) {
        this.byteBuffer = ByteBuffer.allocateDirect(capacity);
    }

    @Override
    public void loadFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            byteBuffer.clear();
            int bytesRead = fileChannel.read(byteBuffer);
            if (bytesRead == -1) {
                throw new IOException("File is empty: " + filePath);
            }
            byteBuffer.flip();
        }
    }

    @Override
    public String readData() {
        if (!byteBuffer.hasRemaining()) {
            throw new RuntimeException("Buffer is empty");
        }
        byteBuffer.rewind();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return new String(bytes);
    }
}

