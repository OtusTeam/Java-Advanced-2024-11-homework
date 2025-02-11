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
@Warmup(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
public class HashServiceBenchmarkTest {

    private final HashService hashService = new HashService();
    private final byte[] password = "password123123123".getBytes();

    @Benchmark
    @OperationsPerInvocation(10000)
    public void md5(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(hashService.getHash(password, HashAlgorithm.MD5));
        }
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void sha256(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(hashService.getHash(password, HashAlgorithm.SHA256));
        }
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void sha512(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(hashService.getHash(password, HashAlgorithm.SHA512));
        }
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {HashServiceBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
 }
