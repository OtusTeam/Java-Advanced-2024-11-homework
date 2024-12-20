package org.example;

import org.example.menu.Emulator;
import java.io.IOException;

public class MainCache {
    public static void main(String[] args) throws IOException {
        Emulator emulator = new Emulator();
        emulator.Run();
    }
}
