package dev.sivakova.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    private PasswordHasher() {
    }

    public static String hash(String input, Algorithm algorithm, int iterations) {
        if (iterations <= 0) {
            throw new IllegalArgumentException("Iterations must be greater than 0.");
        } else {
            try {
                MessageDigest digest = MessageDigest.getInstance(algorithm.getAlgorithmName());
                byte[] result = input.getBytes(StandardCharsets.UTF_8);

                for(int i = 0; i < iterations; ++i) {
                    result = digest.digest(result);
                }

                return bytesToHex(result);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Unsupported hashing algorithm: " + String.valueOf(algorithm), e);
            }
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for(byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static enum Algorithm {
        MD5("MD5"),
        SHA256("SHA-256"),
        SHA512("SHA-512");

        private final String name;

        private Algorithm(String name) {
            this.name = name;
        }

        public String getAlgorithmName() {
            return this.name;
        }
    }
}
