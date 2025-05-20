package ru.otus.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class OffHeapDataStore {

    private final ByteBuffer buffer;

    public OffHeapDataStore() {
        buffer = ByteBuffer.allocateDirect(1024);
    }

    public OffHeapDataStore(int capacity) {
        buffer = ByteBuffer.allocateDirect(capacity);
    }

    public void saveToDirectBuffer(String value) {
        buffer.put(value.getBytes());
    }

    public String loadFromBuffer() {
        buffer.flip();
        byte[] stringBytes = new byte[buffer.remaining()];
        buffer.get(stringBytes);
        return new String(stringBytes, StandardCharsets.UTF_8);
    }
}
