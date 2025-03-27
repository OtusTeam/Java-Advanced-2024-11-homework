package ru.otus.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class MappedByteBufferStore {

    private MappedByteBuffer buffer;
    private RandomAccessFile file;
    private FileChannel fileChannel;

    public MappedByteBufferStore(String filePath) throws IOException {
        file = new RandomAccessFile(filePath, "rw");
        fileChannel = file.getChannel();
        long fileSize = file.length();
        buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize + 1024);
    }

    public String readFromBuffer() {
        // Прокачиваем индекс на начало буфера
        buffer.rewind();
        byte[] byteArray = new byte[buffer.limit()];
        buffer.get(byteArray);

        return new String(byteArray);
    }

    public void writeToBuffer(String data) {
        // Прокачиваем индекс на начало буфера перед записью
        buffer.rewind();
        buffer.put(data.getBytes());
        buffer.flip();  // Важное место: нужно переключить на режим чтения после записи
    }

    public void close() throws IOException {
        fileChannel.close();
        file.close();
    }
}

