package edu.janeforjane.jmh;

import edu.janeforjane.service.HashService;
import lombok.Setter;
import org.openjdk.jmh.annotations.*;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.SingleShotTime)
@Measurement(iterations = 50, batchSize = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Thread)
public class MyBenchmark {

    private HashService hashService;
    @Setter
    private String password;

    @Setup
    public void init() {
        setPassword("mynewprettypassword");
        this.hashService = new HashService();
    }

    @Benchmark
    public void hashMethod_MD5() throws NoSuchAlgorithmException {
        hashService.hashMD5(password);
    }

    @Benchmark
    public void hashMethod_SHA256() throws NoSuchAlgorithmException {
        hashService.hashSHA256(password);
    }

    @Benchmark
    public void hashMethod_SHA51() throws NoSuchAlgorithmException {
        hashService.hashSHA512(password);
    }


}
