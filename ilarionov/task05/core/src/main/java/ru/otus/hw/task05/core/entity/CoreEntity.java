package ru.otus.hw.task05.core.entity;

public class CoreEntity {

    private long id;

    private String userText;

    private String randomText;

    public CoreEntity(String userText, String randomText) {
        this.userText = userText;
        this.randomText = randomText;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserText() {
        return userText;
    }

    public String getRandomText() {
        return randomText;
    }
}

