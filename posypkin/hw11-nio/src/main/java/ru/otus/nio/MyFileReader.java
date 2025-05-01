package ru.otus.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyFileReader {

    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    public void storeFile(String path) {
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get(path))) {
            byteChannel.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromBuffer() {
        buffer.flip();
        byte[] stringBytes = new byte[buffer.remaining()];
        buffer.get(stringBytes);
        return new String(stringBytes);
    }
}
