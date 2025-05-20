package org.example.task7.jmh;

import org.example.task7.hashutil.PasswordHash;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashBenchmarkTest {

    private String password = "Password";

    @Benchmark
    public byte[] md5Hash() {
        return PasswordHash.createPasswordHash(password, "MD5");
    }

    @Benchmark
    public byte[] sha256Hash() {
        return PasswordHash.createPasswordHash(password, "SHA-256");
    }

    @Benchmark
    public byte[] sha512Hash() {
        return PasswordHash.createPasswordHash(password, "SHA-512");
    }
}