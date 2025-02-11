package ru.otus.hw.task07.samples;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Fork(value = 1, warmups = 0)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
public class DefaultStateBenchmarkTest {

    double x = Math.PI;

    @Benchmark
    public void measure() {
        x++;
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {DefaultStateBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
}
