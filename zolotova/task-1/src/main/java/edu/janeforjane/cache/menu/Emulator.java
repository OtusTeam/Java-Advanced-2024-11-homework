package edu.janeforjane.cache.menu;

import edu.janeforjane.cache.datasource.MyDataSource;
import edu.janeforjane.cache.exceptions.CacheDirectoryException;
import edu.janeforjane.cache.exceptions.FileException;
import edu.janeforjane.cache.service.FileService;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Emulator {

    private MyDataSource myDataSource;

    public void start() {

        Scanner scanner = new Scanner(System.in);
        FileService service = null;

        // get service for certain directory
        while (service == null) {

            try {
                System.out.println("Укажите кэшируемую директорию:");// src/main/resources
                String directory = scanner.nextLine();
                System.out.println("Вы ввели: " + directory);

                service = new FileService(directory);

            } catch (CacheDirectoryException e) {
                System.out.println("Указанная директория отсутствует. Попробуйте еще раз.");
            }
        }


        String continueInput;
        do {
            boolean fileFound = false;

            // get file
            while (!fileFound) {

                try {
                    System.out.println("Укажите ключ файла:");
                    String key = scanner.nextLine();

                    String text = new String(service.getData(key), StandardCharsets.UTF_8);
                    System.out.printf(text);
                    fileFound = true;

                } catch (FileException e) {
                    fileFound = false;
                    System.out.println("C получением файла возникла проблема. Попробуйте еще раз.");
                }
            }


            // continue or not
            System.out.println("Хотите продолжить ? (yes/no)");
            continueInput = scanner.nextLine();

            if (continueInput.equalsIgnoreCase("yes")) {
                System.out.println("Хотите запустить gc ? (yes/no)");
                String gc = scanner.nextLine();
                if (gc.equalsIgnoreCase("yes")) {
                    System.gc();
                }
            }

        } while (continueInput.equalsIgnoreCase("yes"));

        scanner.close();

    }
}
