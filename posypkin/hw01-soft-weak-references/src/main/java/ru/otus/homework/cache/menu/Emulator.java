package ru.otus.homework.cache.menu;

import ru.otus.homework.cache.FileCache;

import java.util.Scanner;

public class Emulator {

    //Use: -XX:+UseSerialGC to see usage OldCache
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a cached directory (For example: posypkin/hw01-soft-weak-references/src/test/resources/files/): ");
        String cachedDirectory = scanner.nextLine();
        FileCache fileCache = new FileCache(cachedDirectory);

        while (true) {
            System.out.println("Enter a file name that you want put to the cache (For example: Address.txt)");
            String fileName = scanner.nextLine();
            if (fileName != null && !fileName.isEmpty()) {
                fileCache.put(fileName);
            }

            System.out.println("Enter a file name to get content from the cache (For example: Address.txt)");
            fileName = scanner.nextLine();
            if (fileName != null && !fileName.isEmpty()) {
                System.out.println(fileCache.get(fileName));
            }
            System.gc();
        }
    }
}
