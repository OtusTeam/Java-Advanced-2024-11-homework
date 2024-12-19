package ru.otus;

import ru.otus.domain.Emulator;
import ru.otus.interfaces.impl.SoftCache;
import ru.otus.interfaces.impl.WeakCache;
import ru.otus.interfaces.Cache;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose cache type: 1. SoftCache, 2. WeakCache");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Cache<String, String> cache = (choice == 1) ? new SoftCache<>() : new WeakCache<>();
        Emulator emulator = new Emulator(cache);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Set cache directory");
            System.out.println("2. Load file into cache");
            System.out.println("3. Get file from cache");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter directory path: ");
                    String directoryPath = scanner.nextLine();
                    try {
                        emulator.setCacheDirectory(directoryPath);
                        System.out.println("Cache directory set to: " + directoryPath);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.print("Enter file name to load into cache: ");
                    String fileName = scanner.nextLine();
                    emulator.loadFileIntoCache(fileName);
                }
                case 3 -> {
                    System.out.print("Enter file name to fetch from cache: ");
                    String fileName = scanner.nextLine();
                    emulator.getFileFromCache(fileName);
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    running = false;
                    scanner.close();
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}