package ru.otus.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class PasswordHashUtil {
    public static String hashPassword(String password, String algorithm) {
        try {
            var md = MessageDigest.getInstance(algorithm); // "MD5", "SHA-256", "SHA-512"
            var digest = md.digest(password.getBytes());
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unsupported hash algorithm: " + algorithm, e);
        }
    }
}
