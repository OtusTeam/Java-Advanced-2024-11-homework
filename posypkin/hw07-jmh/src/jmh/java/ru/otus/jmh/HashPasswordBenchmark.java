package ru.otus.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.otus.jmh.service.HashUtil;

import java.util.concurrent.TimeUnit;

public class HashPasswordBenchmark {

    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        @Param({"password1", "password10", "password20", "password30"})
        public String password;

        @Setup(Level.Trial)
        public void setUpTrial() {
            System.out.println("TRIAL: Before " + password);
        }

        @TearDown(Level.Trial)
        public void tearDownTrial() {
            System.out.println("TRIAL: After " + password);
        }

        @Setup(Level.Iteration)
        public void setUpIteration() {
            System.out.println("ITERATION: Before " + password);
        }

        @TearDown(Level.Iteration)
        public void tearDownIteration() {
            System.out.println("ITERATION: After " + password);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0)
    @Measurement(iterations = 2, time = 5)
    public void MD5(ExecutionPlan plan, Blackhole bh) {
        bh.consume(HashUtil.hashPassword(plan.password, "MD5"));
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0)
    @Measurement(iterations = 2, time = 5)
    public void SHA256(ExecutionPlan plan, Blackhole bh) {
        bh.consume(HashUtil.hashPassword(plan.password, "SHA-256"));
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @Fork(value = 1, warmups = 0)
    @Warmup(iterations = 0)
    @Measurement(iterations = 2, time = 5)
    public void SHA512(ExecutionPlan plan, Blackhole bh) {
        bh.consume(HashUtil.hashPassword(plan.password, "SHA-512"));
    }
}
