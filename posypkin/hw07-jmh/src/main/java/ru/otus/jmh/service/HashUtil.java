package ru.otus.jmh.service;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtil {

    @SneakyThrows
    public static String hashPassword(String password, String algorithm) {
        // Получаем экземпляр MessageDigest для выбранного алгоритма
        MessageDigest digest = MessageDigest.getInstance(algorithm);

        // Преобразуем пароль в байты и хешируем
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        // Преобразуем байты хеша в шестнадцатеричную строку
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
