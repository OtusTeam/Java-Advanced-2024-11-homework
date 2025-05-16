
# MyBenchmark

## SingleShotTime

Время однократного выполнения (без прогрева)

```java
@State(Scope.Benchmark)
@BenchmarkMode(Mode.SingleShotTime)
@Measurement(iterations = 50, batchSize = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class MyBenchmark {

    @Benchmark
    public void hashMethod_MD5(){
        hashService.hashMD5(password);
    }
    @Benchmark
    public void hashMethod_SHA256(){
        hashService.hashSHA256(password);
    }
    @Benchmark
    public void hashMethod_SHA51(){
        hashService.hashSHA512(password);
    }
}
```

#### Result:
```text
Benchmark                      Mode  Cnt  Score   Error  Units
MyBenchmark.hashMethod_MD5       ss   50  0.157 ± 0.074  ms/op
MyBenchmark.hashMethod_SHA256    ss   50  0.225 ± 0.098  ms/op
MyBenchmark.hashMethod_SHA51     ss   50  0.306 ± 0.183  ms/op

```

#### Summary:

- MD5 - самый быстрый - `0.157`
- SHA256 - меделеннее чем MD5 - `0.225`
- SHA-512 - медленный чем MD5 - `0.306`




## SampleTime	

Статистика по времени выполнения (процентили)

```java
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class MyBenchmark {

    @Benchmark
    public void hashMethod_MD5(){
        hashService.hashMD5(password);
    }
    @Benchmark
    public void hashMethod_SHA256(){
        hashService.hashSHA256(password);
    }
    @Benchmark
    public void hashMethod_SHA51(){
        hashService.hashSHA512(password);
    }
}
```

#### Result:
```text
Benchmark                                                  Mode     Cnt        Score    Error  Units
MyBenchmark.hashMethod_MD5                               sample  253846     2529.390 ± 32.417  ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.00          sample             2148.000           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.50          sample             2380.000           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.90          sample             2616.000           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.95          sample             2684.000           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.99          sample             4360.000           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.999         sample            26240.000           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p0.9999        sample            31340.205           ns/op
MyBenchmark.hashMethod_MD5:hashMethod_MD5·p1.00          sample          1101824.000           ns/op
MyBenchmark.hashMethod_SHA256                            sample  259182     4874.377 ± 50.032  ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.00    sample             4296.000           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.50    sample             4640.000           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.90    sample             5016.000           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.95    sample             5192.000           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.99    sample             8136.000           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.999   sample            29024.000           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p0.9999  sample            36646.278           ns/op
MyBenchmark.hashMethod_SHA256:hashMethod_SHA256·p1.00    sample          1517568.000           ns/op
MyBenchmark.hashMethod_SHA51                             sample  253222     9941.267 ± 54.519  ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.00      sample             8416.000           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.50      sample             9600.000           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.90      sample            10384.000           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.95      sample            10640.000           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.99      sample            14240.000           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.999     sample            34880.000           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p0.9999    sample           489670.912           ns/op
MyBenchmark.hashMethod_SHA51:hashMethod_SHA51·p1.00      sample          1193984.000           ns/op


```

#### Summary:
- MD5 - самый быстрый - p0.50 = `2380 ns`
- SHA256 - в два раза меделеннее чем MD5 - p0.50 = `4640 ns`
- SHA-512 - самый медленный - p0.50 = `9600 ns`


## Throughput + AverageTime

Throughput - Количество операций в секунду (ops/time)
AverageTime - Среднее время выполнения операции


```java
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 2)
@Fork(1)
@State(Scope.Thread)
public class MyBenchmark {

    @Benchmark
    public void hashMethod_MD5(){
        hashService.hashMD5(password);
    }
    @Benchmark
    public void hashMethod_SHA256(){
        hashService.hashSHA256(password);
    }
    @Benchmark
    public void hashMethod_SHA51(){
        hashService.hashSHA512(password);
    }
}
```

#### Result:
```text
Benchmark                       Mode  Cnt    Score    Error   Units
MyBenchmark.hashMethod_MD5     thrpt    5  408.372 ± 13.676  ops/ms
MyBenchmark.hashMethod_SHA256  thrpt    5  189.053 ±  9.853  ops/ms
MyBenchmark.hashMethod_SHA51   thrpt    5   97.975 ±  7.551  ops/ms
MyBenchmark.hashMethod_MD5      avgt    5    0.003 ±  0.001   ms/op
MyBenchmark.hashMethod_SHA256   avgt    5    0.005 ±  0.001   ms/op
MyBenchmark.hashMethod_SHA51    avgt    5    0.011 ±  0.001   ms/op

```

#### Summary:
  
Throughput (ops/ms):
- MD5: `408` (самый эффективный)
- SHA-256: `189` (в 2 раза медленнее MD5)
- SHA-512: `98` (в 4 раза медленнее MD5)

AverageTime (ms/op):
- MD5: `0.003` (самый быстрый)
- SHA-256: `0.005` (медленнее MD5)
- SHA-512: `0.011` (медленнее MD5)


## Итоговое сравнение алгоритмов

**MD5** - самый быстрый

**SHA-512** - самый медленный

| Алгоритм | SingleShot | SampleTime | Throughput | AverageTime |
|----------|------------|------------|------------|-------------|
| MD5      | 0.4 мс     | 2.38       | 408 ops/ms | 0.003 ms/op |
| SHA-256  | 0.54 мс    | 4.64       | 189 ops/ms | 0.005 ms/op |
| SHA-512  | 0.58 мс    | 9.6        | 98 ops/ms  | 0.011 ms/op |

# Openjdk jmh samples

## JMHSample_02_BenchmarkModes

```text
Benchmark                                                              Mode  Cnt       Score    Error   Units
JMHSample_02_BenchmarkModes.measureAll                                thrpt    5      ≈ 10⁻⁵           ops/us
JMHSample_02_BenchmarkModes.measureMultiple                           thrpt    5      ≈ 10⁻⁵           ops/us
JMHSample_02_BenchmarkModes.measureThroughput                         thrpt    5       9.987 ±  0.004   ops/s
JMHSample_02_BenchmarkModes.measureAll                                 avgt    5  100139.487 ± 26.946   us/op
JMHSample_02_BenchmarkModes.measureAvgTime                             avgt    5  100141.787 ±  4.218   us/op
JMHSample_02_BenchmarkModes.measureMultiple                            avgt    5  100155.146 ± 85.877   us/op
JMHSample_02_BenchmarkModes.measureAll                                   ss       100214.502            us/op
JMHSample_02_BenchmarkModes.measureMultiple                              ss       100202.132            us/op
JMHSample_02_BenchmarkModes.measureSingleShot                            ss       100295.259            us/op
------------------------------------------------
Percentiles (для всех sample тестов):
  min(p0.00) = 100007.936 us
  med(p0.50) = 100139.008 us
  max(p1.00) = 100139.008 us
```