package ru.otus.nio;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static MyFileReader myFileReader = new MyFileReader();
    private static OffHeapDataStore offHeapDataStore = new OffHeapDataStore();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь до файла: ");
        String path = scanner.nextLine();
        myFileReader.storeFile(path);
        String string = myFileReader.readFromBuffer();
        System.out.println(string);
        offHeapDataStore.saveToDirectBuffer(string);
        System.out.println("----------------------   Вывод из DirectBuffer  -----------------------------");
        System.out.println(offHeapDataStore.loadFromBuffer());

        MappedByteBufferStore mappedByteBufferStore = new MappedByteBufferStore(path);
        var str = mappedByteBufferStore.readFromBuffer();

        System.out.println("----------------------   Вывод из MappedByteBuffer  -----------------------------");
        System.out.printf(str);
        mappedByteBufferStore.close();
    }
}
