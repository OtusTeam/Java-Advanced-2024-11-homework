package ru.otus.hw.task07.samples;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Threads(Threads.MAX)
@Fork(value = 1, warmups = 0)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 200, timeUnit = TimeUnit.MILLISECONDS)
public class ConsumeCpuBenchmarkTest {

    @Benchmark
    public void consume0() {
        Blackhole.consumeCPU(0);
    }

    @Benchmark
    public void consume10() {
        Blackhole.consumeCPU(10);
    }

    @Benchmark
    public void consume100() {
        Blackhole.consumeCPU(100);
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {ConsumeCpuBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
}
