package ru.otus.jmh;

import org.openjdk.jmh.annotations.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PasswordHashingBenchmark {

    private static final String PASSWORD = "TestPassword123";

    @Benchmark
    public byte[] hashWithMd5() throws NoSuchAlgorithmException {
        return hashPassword(PASSWORD, "MD5");
    }

    @Benchmark
    public byte[] hashWithSha256() throws NoSuchAlgorithmException {
        return hashPassword(PASSWORD, "SHA-256");
    }

    @Benchmark
    public byte[] hashWithSha512() throws NoSuchAlgorithmException {
        return hashPassword(PASSWORD, "SHA-512");
    }

    private byte[] hashPassword(String password, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
