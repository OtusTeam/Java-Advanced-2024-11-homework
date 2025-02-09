package org.example.task7.jmh;

import org.example.task7.hashutil.PasswordHash;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HashBenchmarkTest {

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    public byte[] md5Hash() {
        return PasswordHash.createPasswordHash("Password", "MD5");
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    public byte[] sha256Hash() {
        return PasswordHash.createPasswordHash("Password", "SHA-256");
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    public byte[] sha512Hash() {
        return PasswordHash.createPasswordHash("Password", "SHA-512");
    }
}
