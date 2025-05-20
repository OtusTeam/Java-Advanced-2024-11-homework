package org.example.menu;

import org.example.ByteBufferImpl;
import org.example.INioBuffer;
import org.example.MappedByteBufferImpl;

import java.io.IOException;
import java.util.Scanner;

public class Emulator {

    Integer bufferSize = Integer.MAX_VALUE;
    INioBuffer buffer;

    private final int BUFFER_SIZE = 1;
    private final int BYTEBUFFER_LOAD = 2;
    private final int MAPPEDBYTEBUFFER_LOAD = 3;
    private final int READ_FILE = 4;
    private String MENU = "Enter 1 (Set buffer size).\nEnter 2 (ByteBuffer:load file).\nEnter 3 (MappedByteBuffer:load file).\nEnter 4 (Read file).\nEnter any number for Exit.";


    public void Run() throws IOException {
        Scanner scanner = new Scanner(System.in);

        boolean run = true;
        while (run) {
            System.out.println(MENU);
            int userChoice = Integer.parseInt(scanner.nextLine());
            System.out.println(userChoice);
            switch (userChoice){
                case BUFFER_SIZE: SetBufferSize(scanner.nextLine()); break;
                case BYTEBUFFER_LOAD: System.out.print("Enter file path (ByteBuffer:load): "); BufferLoad(scanner.nextLine()); break;
                case MAPPEDBYTEBUFFER_LOAD: System.out.print("Enter file path (MappedByteBuffer:load): "); MappedByteBufferLoad(scanner.nextLine()); break;
                case READ_FILE: ReadFile(); break;
                default: run = false; System.out.println("End program"); break;
            }
        }
    }

    private void ReadFile() {
        System.out.println(buffer.readData());
    }

    private void MappedByteBufferLoad(String path) throws IOException {
        buffer = new MappedByteBufferImpl();
        buffer.loadData(path, bufferSize);
    }

    private void BufferLoad(String path) throws IOException {
        buffer = new ByteBufferImpl();
        buffer.loadData(path, bufferSize);
    }

    private void SetBufferSize(String size) {
        try {
            bufferSize = Integer.parseInt(size);
        } catch (NumberFormatException e) {
            System.err.println("SetBufferSize Error: " + e.getMessage());
        }
    }
}
