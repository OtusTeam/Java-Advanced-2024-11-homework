# App for JMH Test

Running profiles:
- unit-test - run unit tests
- jmh - run benchmark tests

## Throughput test

### MD5

![md5_throughput.png](src/main/resources/results/md5_throughput.png)

### SHA256

![sha256_throughput.png](src/main/resources/results/sha256_throughput.png)

### SHA512

![sha512_throughput.png](src/main/resources/results/sha512_throughput.png)

## Average test

### MD5

![md5_average.png](src/main/resources/results/md5_average.png)

### SHA256

![sha256_average.png](src/main/resources/results/sha256_average.png)

### SHA512

![sha512_average.png](src/main/resources/results/sha512_average.png)

## Sampling time test

### MD5

![md5_sampling_time.png](src/main/resources/results/md5_sampling_time.png)

### SHA256

![sha256_sampling_time.png](src/main/resources/results/sha256_sampling_time.png)

### SHA512

![sha512_sampling_time.png](src/main/resources/results/sha512_sampling_time.png)

## Single shot test

### MD5

![md5_single_shot.png](src/main/resources/results/md5_single_shot.png)

### SHA256

![sha256_single_shot.png](src/main/resources/results/sha256_single_shot.png)

### SHA512

![sha512_single_shot.png](src/main/resources/results/sha512_single_shot.png)

## SUMMARY

![summary.png](src/main/resources/results/summary.png)

## Samples

### consume cpu

![samples_consume_cpu.png](src/main/resources/results/samples_consume_cpu.png)

### default state for thread

Comment `@State(Scope.Thread)` and get error

![samples_default_state_for_thread_error.png](src/main/resources/results/samples_default_state_for_thread_error.png)

uncomment and run

![samples_default_state_for_thread.png](src/main/resources/results/samples_default_state_for_thread.png)

### run-to-run test

![samples_run_to_run.png](src/main/resources/results/samples_run_to_run.png)

## User service test

![user_service_test.png](src/main/resources/results/user_service_test.png)

# Summary after review

На Mac OS, чипсете M3 лучше всех показал себя алгоритм SHA256

![summary_after_review.png](src/main/resources/results/summary_after_review.png)