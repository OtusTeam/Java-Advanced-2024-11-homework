package dev.sivakova.nio;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MappedByteBufferStorage implements FileStorage {
    private final MappedByteBuffer buffer;

    public MappedByteBufferStorage(Path filePath, int heapSize) throws IOException {
        long fileSize = Files.size(filePath);
        if (fileSize > heapSize) {
            throw new IllegalArgumentException("File's size exceeds buffer size");
        }

        try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.READ)) {
            buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
        }
    }

    @Override
    public String readFile() {
        buffer.rewind();
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }
}
