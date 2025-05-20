package ru.otus;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class MappedBufferStorage implements OffHeapStorage {

    private final MappedByteBuffer mappedByteBuffer;

    public MappedBufferStorage(String filename, int size) {
        Path path = StorageUtils.validateFilePath(filename);
        if (path == null) {
            throw new IllegalStateException("Невозможно создать буфер: некорректный путь");
        }
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, size);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании MappedBufferStorage: " + e.getMessage(), e);
        }
    }

    @Override
    public void writeByte(int index, byte value) {
        mappedByteBuffer.put(index, value);
    }

    @Override
    public byte readByte(int index) {
        return mappedByteBuffer.get(index);
    }

    @Override
    public String readContent() {
        byte[] bytes = new byte[mappedByteBuffer.remaining()];
        mappedByteBuffer.duplicate().get(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
