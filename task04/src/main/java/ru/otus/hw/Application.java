package ru.otus.hw;

import ru.otus.hw.cache.CacheControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) throws IOException {
        CacheControl cc = new CacheControl();

        while (true) {
            System.out.println("""
                    Choose number:
                    1. Enter directory to cache and Load data from directory to cache
                    2. Enter file name to get data from cache
                    3. exit
                    """);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            Integer num = Integer.parseInt(reader.readLine());
            switch (num) {
                case 1: {
                    System.out.println("""
                            Enter directory to read
                            """);
                    String dir = reader.readLine();
                    cc.loadCache(dir);
                    System.out.println("Data loaded successfully");
                    break;
                }
                case 2: {
                    System.out.println("""
                            Enter file name
                            """);
                    String file = reader.readLine();
                    System.out.println("Data from cache for file :" + file + " : " + cc.get(file));
                    break;
                }
                default: {
                    System.exit(-1);
                }
            }

        }
    }
}