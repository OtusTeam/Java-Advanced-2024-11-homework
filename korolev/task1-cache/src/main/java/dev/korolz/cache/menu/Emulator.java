package dev.korolz.cache.menu;

import dev.korolz.Cache;
import dev.korolz.CacheFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Emulator {
    private final Cache<String, String> cache;
    private final BufferedReader reader;

    public Emulator(CacheFactory.CacheType cacheType) {
        this.cache = CacheFactory.createCache(cacheType);
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() throws IOException {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = reader.readLine().trim();

            try {
                switch (choice) {
                    case "1" -> setCacheDirectory();
                    case "2" -> loadFile();
                    case "3" -> getFileContent();
                    case "4" -> running = false;
                    default -> System.out.println("Неверный выбор. Пожалуйста, выберите из предложенных вариантов.");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Программа завершена.");
    }

    private void printMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1. Указать кэшируемую директорию");
        System.out.println("2. Загрузить содержимое файла в кэш");
        System.out.println("3. Получить содержимое файла из кэша");
        System.out.println("4. Выход");
        System.out.print("Введите ваш выбор: ");
    }

    private void setCacheDirectory() throws IOException {
        System.out.print("Введите путь к директории: ");
        String path = reader.readLine().trim();

        // Проверка существования директории
        if (!Files.exists(Path.of(path)) || !Files.isDirectory(Path.of(path))) {
            throw new IOException("Указанная директория не существует или не является директорией");
        }

        cache.setCacheDirectory(path);
        System.out.println("Кэшируемая директория успешно установлена: " + path);
    }

    private void loadFile() throws IOException {
        System.out.print("Введите имя файла: ");
        String fileName = reader.readLine().trim();

        cache.load(fileName);
        System.out.println("Файл успешно загружен в кэш: " + fileName);
    }

    private void getFileContent() throws IOException {
        System.out.print("Введите имя файла: ");
        String fileName = reader.readLine().trim();

        String content = cache.get(fileName);
        System.out.println("\n==== Содержимое файла " + fileName + " ====");
        System.out.println(content);
        System.out.println("=====================================");
    }

    public static void main(String[] args) {
        try {
            System.out.println("Выберите тип кэша:");
            System.out.println("1. Weak Reference Cache");
            System.out.println("2. Soft Reference Cache");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String choice = reader.readLine().trim();

            CacheFactory.CacheType cacheType;
            if ("1".equals(choice)) {
                cacheType = CacheFactory.CacheType.WEAK;
                System.out.println("Выбран Weak Reference Cache");
            } else {
                cacheType = CacheFactory.CacheType.SOFT;
                System.out.println("Выбран Soft Reference Cache");
            }

            Emulator emulator = new Emulator(cacheType);
            emulator.start();

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}