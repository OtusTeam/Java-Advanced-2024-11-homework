# Spring boot приложение. (утечка памяти) 

### Описание:
* сборка mvn clean package
* Запустить можно плагином  (task7-->Plugins-->jmh:jmh)

### Результаты работы алгоритмов md5, sha256, sha512:
![md5_res.JPG](res/md5_res.JPG)

![sha256_res.JPG](res/sha256_res.JPG)

![sha512_res.JPG](res/sha512_res.JPG)

В результате видно что оптимально использовать алгоритм sha256 
(Throughput -операций в секунду), на железе CPU: (i5-14600K), RAM: (32 Gb) 
![sum_res.JPG](res/sum_res.JPG)


### 3 различых теста:
JMHSample_02_BenchmarkModes:
![02_benchmark_modes.JPG](res/02_benchmark_modes.JPG)

JMHSample_26_BatchSize Влияние размера batch на производительность:
![26_batch_size.JPG](res/26_batch_size.JPG)

JMHSample_32_BulkWarmup прогрев:
![32_bulk_warmup.JPG](res/32_bulk_warmup.JPG)


### По всем тестам можно поссмотреть в результирующем файле percentiles:

![percentiles.JPG](res/percentiles.JPG)




