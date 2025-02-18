package org.example.task7.hashutil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {

    public static byte[] createPasswordHash(String password, String algorithm) {
        try {
            var md = MessageDigest.getInstance(algorithm);
            var digest = md.digest(password.getBytes());
            return digest;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("NoSuchAlgorithmException algorithm: '%s' err: '%s' ",algorithm, e));
        }
    }
}
