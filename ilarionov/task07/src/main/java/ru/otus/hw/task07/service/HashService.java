package ru.otus.hw.task07.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.task07.model.HashAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class HashService {

    public String getHash(byte[] password, HashAlgorithm algorithm) {
        try {
            return new String(MessageDigest.getInstance(algorithm.name()).digest(password), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
