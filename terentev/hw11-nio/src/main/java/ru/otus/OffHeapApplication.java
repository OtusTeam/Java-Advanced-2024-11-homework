package ru.otus;

public class OffHeapApplication {

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            running = StorageConsole.printMenu();
        }
    }
}
