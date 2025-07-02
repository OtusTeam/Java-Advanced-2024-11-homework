package dev.sivakova.core.model;

public class FileInMemory {
    private final String name;
    private final byte[] content;

    public FileInMemory(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}
