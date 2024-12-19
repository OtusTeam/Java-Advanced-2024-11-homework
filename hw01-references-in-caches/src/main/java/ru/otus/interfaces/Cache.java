package ru.otus.interfaces;

public interface Cache<K, V> {

    V get(K key);

    void load(K key, V value);
}