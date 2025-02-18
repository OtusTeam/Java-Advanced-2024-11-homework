package ru.otus.hw.task07.samples;


import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 0)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
public class RunToRunBenchmarkTest {

    @State(Scope.Thread)
    public static class SleepyState {
        public long sleepTime;

        @Setup
        public void setup() {
            sleepTime = (long) (Math.random() * 1000);
        }
    }

    @Benchmark
    @Fork(1)
    public void baseline(SleepyState s) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Benchmark
    @Fork(5)
    public void fork_1(SleepyState s) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Benchmark
    @Fork(20)
    public void fork_2(SleepyState s) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(s.sleepTime);
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {RunToRunBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
}
