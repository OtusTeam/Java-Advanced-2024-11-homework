package org.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ByteBufferImpl implements INioBuffer {

    private ByteBuffer byteBuffer;

    @Override
    public void loadData(String filePath, int bufferSize) throws IOException {
        Util.checkSize(filePath, bufferSize);
        Path path = Path.of(filePath);
        long size = Files.size(path);
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            byteBuffer = ByteBuffer.allocateDirect((int) size);
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
        }
    }

    @Override
    public String readData() {
        byteBuffer.rewind();
        return StandardCharsets.UTF_8.decode(byteBuffer).toString();
    }
}
