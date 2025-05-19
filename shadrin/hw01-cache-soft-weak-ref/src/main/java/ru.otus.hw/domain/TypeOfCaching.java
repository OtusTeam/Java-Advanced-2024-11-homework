package ru.otus.hw.domain;

public enum TypeOfCaching {

    SOFT_REF("SoftReference"),
    WEAK_REF("WeakReference");

    private final String value;

    TypeOfCaching(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
