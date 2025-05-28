package ru.otus.hw.task05.service.dto;

public class ServiceDto {
    private long id;

    private String userText;

    private String randomText;

    public ServiceDto(long id, String userText, String randomText) {
        this.id = id;
        this.userText = userText;
        this.randomText = randomText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getRandomText() {
        return randomText;
    }

    public void setRandomText(String randomText) {
        this.randomText = randomText;
    }
}
