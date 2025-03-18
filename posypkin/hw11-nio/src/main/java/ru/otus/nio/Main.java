package ru.otus.nio;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    private static FileReader fileReader = new FileReader();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь до файла: ");
        String path = scanner.nextLine();
        var string = fileReader.readFile(path);
        System.out.println(string);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        buffer.put(string.getBytes());
        buffer.flip();
        byte[] stringBytes = new byte[buffer.remaining()];
        buffer.get(stringBytes);
        System.out.println(new String(stringBytes, StandardCharsets.UTF_8));
    }
}
