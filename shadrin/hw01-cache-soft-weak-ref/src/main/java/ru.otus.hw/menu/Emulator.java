package ru.otus.hw.menu;

import ru.otus.hw.domain.TypeOfCaching;
import ru.otus.hw.service.CustomFileReader;
import ru.otus.hw.service.impl.CustomFileReaderImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Emulator {

    private final CustomFileReader fileReader;

    public Emulator() {
        this.fileReader = new CustomFileReaderImpl();
    }

    public void run() {
        System.out.println("Start emulator");
        printCommands();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean finishProcess = false;
            while(!finishProcess) {
                System.out.println("\nEnter the command");
                String[] commands = reader.readLine().split(" ");
                switch (commands[0]) {
                    case "get_type_caching":
                        TypeOfCaching currentTypeOfCaching = fileReader.getCurrentTypeOfCaching();
                        System.out.println(currentTypeOfCaching != null ?
                                "Current caching mode - " + currentTypeOfCaching.getValue() :
                                "The cache is not configured yet");
                        break;
                    case "set_type_caching":
                        if (commands[1].equals("soft")) {
                            fileReader.configureCaching(TypeOfCaching.SOFT_REF);
                        } else if (commands[1].equals("weak")) {
                            fileReader.configureCaching(TypeOfCaching.WEAK_REF);
                        } else {
                            printErrorEnteringCommand();
                        }
                        break;
                    case "start_gc":
                        System.gc();
                        System.out.println("The garbage collector was started");
                        break;
                    case "clear_cache":
                        fileReader.clearCache();
                        break;
                    case "get_content":
                        if (commands.length > 2) {
                            printErrorEnteringCommand();
                        }
                        System.out.println(fileReader.getContent(commands[1]));
                        break;
                    case "get_commands":
                        printCommands();
                        break;
                    case "finish":
                        finishProcess = true;
                        break;
                    default:
                        printErrorEnteringCommand();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void printCommands() {
        System.out.println("COMMAND                             | DESCRIBE");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("get_commands                        | Get a description of the commands");
        System.out.println("get_type_caching                    | Gets the current type of caching");
        System.out.println("set_type_caching <soft/weak>        | Sets the type of caching");
        System.out.println("start_gc                            | Starting the garbage collector");
        System.out.println("clear_cache                         | Forced cache cleanup");
        System.out.println("get_content <Names.txt/Address.txt> | Get the file data");
        System.out.println("finish                              | Completes the emulation process");
        System.out.println("----------------------------------------------------------------------------");
    }

    private void printErrorEnteringCommand() {
        System.out.println("The command was entered incorrectly!");
    }
}
