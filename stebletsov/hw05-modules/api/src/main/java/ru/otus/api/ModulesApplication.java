package ru.otus.api;

import ru.otus.service.Service;

import java.util.stream.IntStream;

public class ModulesApplication {
    public static void main(String[] args) {
        // save 10 records
        IntStream.range(0, 10)
                .forEach(i -> Service.saveData());

        // print all entities
        Service.getAllData().forEach(System.out::println);
    }
}
