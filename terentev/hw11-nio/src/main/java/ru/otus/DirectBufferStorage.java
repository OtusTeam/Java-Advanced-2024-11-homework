package ru.otus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DirectBufferStorage implements OffHeapStorage {

    private final ByteBuffer byteBuffer;

    public DirectBufferStorage(String filename, int size) {
        Path path = StorageUtils.validateFilePath(filename);

        if (path == null) {
            throw new IllegalStateException("Невозможно создать буфер: некорректный путь");
        }

        byteBuffer = ByteBuffer.allocateDirect(size);

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            fileChannel.read(byteBuffer);
            byteBuffer.flip();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage(), e);
        }
    }

    @Override
    public void writeByte(int index, byte value) {
        byteBuffer.put(index, value);
    }

    @Override
    public byte readByte(int index) {
        return byteBuffer.get(index);
    }

    @Override
    public String readContent() {
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.duplicate().get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
