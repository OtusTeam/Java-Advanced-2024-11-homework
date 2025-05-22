package org.example;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MappedByteBufferImpl implements INioBuffer {

    private MappedByteBuffer byteBuffer;

    @Override
    public void loadData(String filePath, int bufferSize) throws IOException {
        Util.checkSize(filePath, bufferSize);
        Path path = Path.of(filePath);
        long fileSize = Files.size(path);
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize);
        }
    }

    @Override
    public String readData() {
        byteBuffer.rewind();
        return StandardCharsets.UTF_8.decode(byteBuffer).toString();
    }
}
