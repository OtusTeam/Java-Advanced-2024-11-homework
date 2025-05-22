package ru.otus.provider;

import java.util.UUID;

public class Provider {
    public static String provideData() {
        return "Provider data: " + UUID.randomUUID();
    }
}
