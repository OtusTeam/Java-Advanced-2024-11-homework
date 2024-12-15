package cache.menu;

import cache.Cache;
import cache.CacheImpl;

import java.util.Scanner;

public class Emulator {
    private final Cache fileCache = new CacheImpl();

    public void start() {
        var scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command: ");
            System.out.println("1. Set cache directory");
            System.out.println("2. Load file into cache");
            System.out.println("3. Get file content");
            System.out.println("4. Exit");

            var option = scanner.nextLine();
            switch (option) {
                case "1" -> setDirectory(scanner);
                case "2" -> loadFile(scanner);
                case "3" -> getContent(scanner);
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void setDirectory(Scanner scanner) {
        System.out.println("Enter directory:");
        String path = scanner.nextLine();
        try {
            fileCache.setCacheDirectory(path);
            System.out.println("Cache directory set to: " + path);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loadFile(Scanner scanner) {
        System.out.println("Enter file name:");
        var fileName = scanner.nextLine();
        try {
            fileCache.loadFileIntoCache(fileName);
            System.out.println("File loaded into cache: " + fileName);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getContent(Scanner scanner) {
        System.out.println("Enter file name:");
        var fileName = scanner.nextLine();
        try {
            var content = fileCache.getFileContent(fileName);
            System.out.println("Content of " + fileName + ":\n" + content);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
