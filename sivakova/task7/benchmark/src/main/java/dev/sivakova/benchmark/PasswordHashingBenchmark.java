package dev.sivakova.benchmark;

import dev.sivakova.util.PasswordHasher;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class PasswordHashingBenchmark {

    @Param({"password123"}) private String password;

    @Param({"SHA256", "SHA512", "MD5"})
    private String algorithm;

    @Param("5000")
    private int iterations;

    @Benchmark
    public String hashPassword() {
        PasswordHasher.Algorithm algo = PasswordHasher.Algorithm.valueOf(algorithm);
        return PasswordHasher.hash(password, algo, iterations);
    }
}
