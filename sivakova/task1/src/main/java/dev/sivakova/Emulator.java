package dev.sivakova;

import dev.sivakova.cache.FileCache;

import java.util.Scanner;

public class Emulator {
    private final FileCache fileCache = new FileCache();

    public void start() {
        var scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command: ");
            System.out.println("1. Set cache directory");
            System.out.println("2. Get file content");
            System.out.println("3. Exit");

            var option = scanner.nextLine();
            switch (option) {
                case "1" -> setFileDirectory(scanner);
                case "2" -> getFileContent(scanner);
                case "3" -> {
                    return;
                }
                default -> System.out.println("Unrecognized command");
            }
        }
    }

    private void setFileDirectory(Scanner scanner) {
        System.out.println("Enter directory path:");
        String path = scanner.nextLine();
        try {
            fileCache.setDirectory(path);
            System.out.println("File directory set to: " + path);
        } catch (Exception e) {
            System.out.println("Error setting file directory: " + e.getMessage());
        }
    }
    private void getFileContent(Scanner scanner) {
        System.out.println("Enter file name:");
        var fileName = scanner.nextLine();
        try {
            var content = fileCache.get(fileName);
            System.out.println("Content of " + fileName + ":\n" + content);
        } catch (Exception e) {
            System.out.println("Error getting file content: " + e.getMessage());
        }
    }
}