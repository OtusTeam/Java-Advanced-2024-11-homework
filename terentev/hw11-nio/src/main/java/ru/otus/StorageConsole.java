package ru.otus;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class StorageConsole {

    private static int storageSize = 1024;
    private final static Map<String, OffHeapStorage> storageMap = new HashMap<>();

    public static boolean printMenu() {
        System.out.println("""
                1 - Загрузить файл в DirectBufferStorage
                2 - Загрузить файл в MappedBufferStorage
                3 - Установить размер off-heap хранилища
                4 - Показать содержимое файла
                5 - Выйти
                """);

        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1" -> loadStorage(DirectBufferStorage.class);
            case "2" -> loadStorage(MappedBufferStorage.class);
            case "3" -> setStorageSize();
            case "4" -> displayContent();
            case "5" -> {
                System.out.println("Выходим из программы");
                return false;
            }
            default -> System.out.println("Неизвестная команда");
        }
        return true;
    }

    private static void loadStorage(Class<? extends OffHeapStorage> storageClass) {
        String filename = getFileName();
        try {
            OffHeapStorage storage = storageClass
                    .getConstructor(String.class, int.class)
                    .newInstance(filename, storageSize);
            storageMap.put(filename, storage);
            System.out.println("Хранилище создано для файла: " + filename);
        } catch (Exception e) {
            System.out.println("Ошибка при создании хранилища: " + e.getMessage());
        }
    }

    private static void displayContent() {
        String filename = getFileName();
        if (storageMap.containsKey(filename)) {
            System.out.println(storageMap.get(filename).readContent());
        } else {
            System.out.println("Не найдено хранилище для файла: " + filename);
        }
    }

    private static void setStorageSize() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите размер хранилища (в байтах): ");
            storageSize = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Некорректный размер");
        }
    }

    private static String getFileName() {
        System.out.print("Введите имя файла: ");
        return new Scanner(System.in).nextLine();
    }
}
