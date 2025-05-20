package ru.otus.hw.domain;

import java.time.LocalDateTime;

public record FileContent(LocalDateTime dateTimeOfDataReading, String content) {

    @Override
    public String toString() {
        return "dateTimeOfDataReading: " + dateTimeOfDataReading + "\n" +
                "content:\n" + content;
    }
}