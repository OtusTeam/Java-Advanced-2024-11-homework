package ru.otus.hw.task07.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.otus.hw.task07.entity.User;
import ru.otus.hw.task07.model.HashAlgorithm;
import ru.otus.hw.task07.repository.UserRepository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@State(Scope.Benchmark)
@Threads(Threads.MAX)
@BenchmarkMode(Mode.Throughput)
@Fork(value = 1, warmups = 0)
@Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@ExtendWith(MockitoExtension.class)
public class UserServiceBenchmarkTest {

    private UserService userService;

    private final String name = "testName";
    private final byte[] password = "password123123123".getBytes();

    @Setup
    public void before() {
        var hashService = new HashService();
        var userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, hashService);
        when(userRepository.save(any())).thenReturn(new User());
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void md5(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(userService.save(name, password, HashAlgorithm.MD5));
        }
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void sha256(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(userService.save(name, password, HashAlgorithm.SHA256));
        }
    }

    @Benchmark
    @OperationsPerInvocation(10000)
    public void sha512(Blackhole bh) {
        for (int i = 0; i < 10000; i++) {
            bh.consume(userService.save(name, password, HashAlgorithm.SHA512));
        }
    }

    @Test
    void runBenchmarks() throws IOException {
        String[] argv = {UserServiceBenchmarkTest.class.getName()};
        org.openjdk.jmh.Main.main(argv);
    }
 }
