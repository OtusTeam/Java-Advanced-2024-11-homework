package dev.sivakova.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ByteBufferStorage implements FileStorage {
    private final ByteBuffer buffer;

    public ByteBufferStorage(Path filePath, int capacity) throws IOException {
        FileStorageUtil.validatePath(filePath);
        long fileSize = Files.size(filePath);
        if (fileSize > capacity) {
            throw new IllegalArgumentException("File's size exceeds buffer capacity");
        }

        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            buffer = ByteBuffer.allocateDirect((int) fileSize);
            channel.read(buffer);
            buffer.flip();
        }
    }

    @Override
    public String readFile() {
        buffer.rewind();
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }
}
