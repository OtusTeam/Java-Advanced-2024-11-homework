package ru.otus.nio;

import java.lang.StringBuilder;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    public String readFile(String path) {
        int count;
        StringBuilder builder = new StringBuilder();
        try (SeekableByteChannel byteChannel = Files.newByteChannel(Paths.get(path))) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            do {
                count = byteChannel.read(buffer);
                if (count != -1) {
                    buffer.rewind();
                    for (int i = 0; i < count; i++) {
                        builder.append((char) buffer.get());
                    }
                }
            } while (count != -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
