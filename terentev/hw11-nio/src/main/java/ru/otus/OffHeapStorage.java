package ru.otus;

public interface OffHeapStorage {

    String readContent();

    void writeByte(int index, byte value);

    byte readByte(int index);
}
