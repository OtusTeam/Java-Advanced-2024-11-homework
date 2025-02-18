package ru.otus.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import ru.otus.util.PasswordHashUtil;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Fork(value = 3, warmups = 1)
@Measurement(iterations = 5, time = 1)
public class PasswordHashBenchmark {
    @Param({"MD5", "SHA-256", "SHA-512"})
    public String algorithm;

    public String password = "longPassword12345";


    @Benchmark
    public String benchmarkHashPassword() {
        return PasswordHashUtil.hashPassword(password, algorithm);
    }

}
