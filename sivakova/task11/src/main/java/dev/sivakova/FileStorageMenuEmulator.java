package dev.sivakova;

import dev.sivakova.nio.ByteBufferStorage;
import dev.sivakova.nio.FileStorage;
import dev.sivakova.nio.MappedByteBufferStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileStorageMenuEmulator {
    private final Scanner scanner = new Scanner(System.in);
    private FileStorage fileStorage;
    private Path filePath;
    private int bufferSize;

    public void start() {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Select File and Buffer Size");
            System.out.println("2. Choose Implementation (Direct or Mapped)");
            System.out.println("3. Read File Content");
            System.out.println("4. Reset File/Implementation");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    selectFileAndBufferSize();
                    break;
                case 2:
                    chooseImplementation();
                    break;
                case 3:
                    readFileContent();
                    break;
                case 4:
                    reset();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void selectFileAndBufferSize() {
        System.out.print("Enter file path: ");
        filePath = Path.of(scanner.nextLine());
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            System.out.println("Invalid file path!");
            filePath = null;
            return;
        }

        System.out.print("Enter buffer size in bytes (max 2GB): ");
        bufferSize = scanner.nextInt();
        scanner.nextLine();
        System.out.println("File and buffer size selected.");
    }

    private void chooseImplementation() {
        if (filePath == null) {
            System.out.println("Select a file first.");
            return;
        }

        System.out.println("Choose implementation:");
        System.out.println("1. Direct ByteBuffer");
        System.out.println("2. Mapped ByteBuffer");

        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            if (choice == 1) {
                fileStorage = new ByteBufferStorage(filePath, bufferSize);
                System.out.println("DirectByteBufferStorage selected.");
            } else if (choice == 2) {
                fileStorage = new MappedByteBufferStorage(filePath, bufferSize);
                System.out.println("MappedByteBufferStorage selected.");
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private void readFileContent() {
        if (fileStorage == null) {
            System.out.println("Choose an implementation first.");
            return;
        }

        try {
            String content = fileStorage.readFile();
            System.out.println("File Content:\n" + content);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void reset() {
        fileStorage = null;
        filePath = null;
        bufferSize = 0;
        System.out.println("File and implementation reset.");
    }
}
