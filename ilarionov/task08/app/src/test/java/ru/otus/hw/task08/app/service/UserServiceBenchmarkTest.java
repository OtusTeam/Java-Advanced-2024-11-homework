package ru.otus.hw.task08.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import ru.otus.hw.task08.app.entity.User;
import ru.otus.hw.task08.app.model.HashAlgorithm;
import ru.otus.hw.task08.app.repository.UserRepository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@State(Scope.Benchmark)
@Threads(Threads.MAX)
@BenchmarkMode(Mode.Throughput)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@ExtendWith(MockitoExtension.class)
public class UserServiceBenchmarkTest {

    private UserService userService;

    @Param({"MD5", "SHA256", "SHA512"})
    public HashAlgorithm algorithm;

    @Param({"password123", "dsfa@#$afsda123@#$asd123asf"})
    public String password;

    @Setup
    public void before() {
        var hashService = new HashService();
        var userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, hashService);
        when(userRepository.save(any())).thenReturn(new User());
    }

    @Benchmark
    public void hash(Blackhole bh) {
        bh.consume(userService.save("testName", password.getBytes(), algorithm));
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {UserServiceBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
 }
