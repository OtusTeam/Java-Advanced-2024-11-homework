package org.example.menu;

import org.example.MyCache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Emulator {

    private final MyCache cache;
    private String basePath;

    private int DIR_PATH = 1;
    private int GET_VALUE = 2;
    private String MENU = "Enter 1 (directory path).\nEnter 2 (get value by filename).\nEnter any number for Exit.";


    public Emulator() {
        cache = new MyCache();
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getCachedValue(String key) {
        return cache.get(key);
    }

    public void setCachedValue(String fileName, String data) {
        cache.put(fileName, data);
    }

    public String getValueByFileName(String fileName) throws IOException {
        String value =getCachedValue(fileName);
        if (value == null) {
            value = Files.readString(Paths.get(basePath, fileName));
            setCachedValue(fileName, value);
        }
        return value;
    }

    public void Run() throws IOException {
        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        while (run) {
            System.out.println(MENU);
            int userChoice = Integer.parseInt(scanner.nextLine());
            System.out.println(userChoice);
            if (DIR_PATH == userChoice) {
                setBasePath(scanner.nextLine());
            } else if (GET_VALUE == userChoice) {
                System.out.println(getValueByFileName(scanner.nextLine()));
            } else {
                run = false;
                System.out.println("End program");
            }
        }
    }
}
