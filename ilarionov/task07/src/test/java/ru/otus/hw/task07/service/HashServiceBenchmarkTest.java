package ru.otus.hw.task07.service;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.otus.hw.task07.model.HashAlgorithm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Threads(Threads.MAX)
@BenchmarkMode(Mode.All)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 2, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
public class HashServiceBenchmarkTest {

    private final HashService hashService = new HashService();

    @Param({"MD5", "SHA256", "SHA512"})
    public HashAlgorithm algorithm;

    @Param({"password123", "dsfa@#$afsda123@#$asd123asf"})
    public String password;

    @Benchmark
    @OperationsPerInvocation(10000)
    public void hashing(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(hashService.getHash(password.getBytes(), algorithm));
        }
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {HashServiceBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
 }
