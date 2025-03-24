package ru.otus.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class MappedByteBufferStore {

    private MappedByteBuffer buffer;
    private RandomAccessFile file;
    private FileChannel fileChannel;

    public MappedByteBufferStore(String filePath) throws IOException {
        file = new RandomAccessFile(filePath, "r");
        fileChannel = file.getChannel();

        long fileSize = file.length();

        buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
    }

    public String readFromBuffer() {
        byte[] byteArray = new byte[buffer.limit()];
        buffer.get(byteArray);

        return new String(byteArray);
    }

    public void close() throws IOException {
        fileChannel.close();
        file.close();
    }
}
