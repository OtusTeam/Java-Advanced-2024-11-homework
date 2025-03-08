package ru.otus.menu;

import ru.otus.ByteBufferStorageImpl;
import ru.otus.MappedByteBufferStorageImpl;
import ru.otus.OffHeapStorage;

import java.io.IOException;
import java.util.Scanner;

public class Emulator {
    private OffHeapStorage storage;
    private final Scanner scanner;
    private int bufferSize = 512;

    public Emulator() {
        scanner = new Scanner(System.in);
    }

    public void start() {

        while (true) {
            System.out.println("\nEnter command:");
            System.out.println("1. Set OffHeapStorage size");
            System.out.println("2. Load file using ByteBuffer");
            System.out.println("3. Load file using MappedByteBuffer");
            System.out.println("4. Get file content");
            System.out.println("5. Exit");

            var option = scanner.nextLine();
            switch (option) {
                case "1" -> setOffHeapStorageSize();
                case "2" -> loadFileUsingByteBuffer();
                case "3" -> loadFileUsingMappedByteBuffer();
                case "4" -> getFileContent();
                case "5" -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void setOffHeapStorageSize() {
        bufferSize = Integer.parseInt(scanner.nextLine());
    }

    private void loadFileUsingByteBuffer() {
        storage = new ByteBufferStorageImpl(bufferSize);
        loadFile();
    }

    private void loadFileUsingMappedByteBuffer() {
        storage = new MappedByteBufferStorageImpl(bufferSize);
        loadFile();
    }

    private void loadFile() {
        System.out.print("Enter file name: ");
        var fileName = scanner.nextLine();

        try {
            storage.loadFile(fileName);
            System.out.println("File loaded " + storage.getClass().getSimpleName());
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    private void getFileContent() {
        try {
            System.out.println("\nFile content:");
            System.out.println(storage.readData());
        } catch (Exception e) {
            System.err.println("Error reading file content: " + e.getMessage());
        }
    }
}
