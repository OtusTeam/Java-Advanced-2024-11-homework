# Сервис регистрации пользователей с JMH-бенчмарков для алгоритмов хеширования
Этот проект реализует микросервис для регистрации пользователей
и измерения производительности различных алгоритмов хеширования пароля
с использованием **JMH** (Java Microbenchmark Harness).
## Функциональность
- Регистрация пользователей с сохранением хеша пароля в БД
- Benchmark-тестирование производительности алгоритмов:
    - `AverageTime`
    - `SampleTime`
    - `Throughput`
    - `SingleShotTime`
- Допонительно реализованы примеры JMH-бенчмарков для:
    - `Branch Prediction`
    - `Cache Access Patterns`
    - `Per-Invoke Setup`
## Архитектура
- `UserController` — REST API для регистрации
- `UserService` — бизнес-логика регистрации и хеширования
- `PasswordHasher` — обёртка над алгоритмами
- `PasswordHashingBenchmark` — класс JMH-бенчмарков
- `Встроенный H2` — база данных для хранения пользователей
## Запуск приложения
В директории core выполните команду для сборки проекта:
```
mvn clean spring-boot:run
```
## Запуск JMH-бенчмарков
Для запуска JMH-бенчмарков необходимо собрать соответствующий модуль, выполнив команду:
```
mvn clean install -pl benchmark -am
```
### Запуск JMH-бенчмарков алгоритмов хеширования
Ниже приведены команды для запуска JMH-бенчмарков с соответствующими параметрами и количеством итераций алгоритма хеширования - 10000.
#### Throughput
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar -bm Throughput dev.sivakova.benchmark.PasswordHashingBenchmark -p iterations=10000
```
#####  Результаты (режим Throughput)

| Алгоритм | Среднее значение | Погрешность (99.9%) | Мин. значение | Макс. значение | Стандартное отклонение |
|----------|------------------|----------------------|----------------|----------------|--------------------------|
| **SHA-256** | 1424,396 ops/s | ±59,544 ops/s       | 1249,180 ops/s | 1511,804 ops/s | 79,490                   |
| **SHA-512** |  440,942 ops/s | ±7,328 ops/s         | 394,838 ops/s  | 446,610 ops/s  | 9,783                    |
| **MD5**     | 1184,445 ops/s | ±6,649 ops/s         | 1163,944 ops/s | 1201,460 ops/s | 8,876                    |
Вывод: **SHA-256** показал наивысшую производительность среди безопасных алгоритмов, но **MD5** был быстрее, хотя и не рекомендуется для реального использования из-за уязвимостей.
### AverageTime
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar -bm AverageTime dev.sivakova.benchmark.PasswordHashingBenchmark -p iterations=10000
```
#### Результаты (режим AverageTime)
| Алгоритм   | Среднее время | Погрешность |
|------------|----------------|-------------|
| **SHA-256** | 0,001 s/op      | ±0,001       |
| **SHA-512** | 0,002 s/op      | ±0,001       |
| **MD5**     | 0,001 s/op      | ±0,001       |
Вывод: **SHA-256** и **MD5** показывают одинаковое среднее время выполнения, однако **SHA-512** требует в 2 раза больше времени, что важно учитывать при выборе алгоритма в зависимости от требований к безопасности и производительности.

### SampleTime
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar -bm SampleTime dev.sivakova.benchmark.PasswordHashingBenchmark -p iterations=10000
```
#### Результаты (режим SampleTime)
Время в секундах на операцию:

| Алгоритм | Среднее | Погрешность | p0.00 | p0.50 | p0.90 | p0.95 | p0.99 | p0.999 | p0.9999 | p1.00  |
|----------|---------|-------------|-------|-------|-------|-------|-------|--------|---------|--------|
| SHA-256  | 0.001   | ±0.001      | 0.001 | 0.001 | 0.001 | 0.001 | 0.001 | 0.002  | 0.002   | 0.033  |
| SHA-512  | 0.002   | ±0.001      | 0.002 | 0.002 | 0.002 | 0.003 | 0.004 | 0.007  | 0.021   | 0.048  |
| MD5      | 0.001   | ±0.001      | 0.001 | 0.001 | 0.001 | 0.001 | 0.001 | 0.002  | 0.003   | 0.035  |

Вывод:
1. **SHA-256** и **MD5** показывают одинаковое среднее время (0.001 s/op)
2. **MD5** быстрее на высоких перцентилях, но небезопасен
3. **SHA-512** в 2 раза медленнее в среднем (0.002 s/op)
4. Все алгоритмы имеют редкие выбросы (~0.03-0.05s) на p1.00

### SingleShot
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar -bm SingleShotTime dev.sivakova.benchmark.PasswordHashingBenchmark -p iterations=10000
```
#### Результаты тестирования алгоритмов хеширования (SingleShot режим)
| Алгоритм | Среднее время | Погрешность | Относительная скорость |
|----------|---------------|-------------|------------------------|
| MD5      | 0.017 с       | ±0.006 с    | 1.00x (база)           | 
| SHA-256  | 0.026 с       | ±0.016 с    | 0.65x                  | 
| SHA-512  | 0.031 с       | ±0.003 с    | 0.55x                  |

#### Итоговый анализ производительности алгоритмов хеширования

| Алгоритм | Throughput (ops/s) | Avg Time (s/op) | SampleTime (перцентили) | SingleShot (10k ops) |
|----------|-------------------|-----------------|--------------------------|---------------------|
| MD5      | 1184              | 0.001           | p0.999: 0.002s           | 0.017s              |
| SHA-256  | 1424              | 0.001           | p0.999: 0.002s           | 0.026s              |
| SHA-512  | 441               | 0.002           | p0.999: 0.007s           | 0.031s              |

#### Выбранный алгоритм: SHA-256
#### Обоснование выбора:
**1. По производительности:**
- **Throughput:** 1424 ops/s (лучший среди безопасных алгоритмов)
- **Задержки:** 99.9% запросов ≤2ms (аналогично MD5)
- **Стабильность:** Минимальный разброс результатов (±0.001s)
**2. По безопасности:**
- Достаточная криптостойкость для большинства систем
- Отсутствие известных уязвимостей (в отличие от MD5)
- Поддержка всеми современными платформами


### Запуск Benchmark OpenJDK
После сборки необходимо запустить benchmark с помощью JMH.
Ниже приведены команды для запуска трех benchmark классов
#### JMHSample_36_BranchPrediction
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar  dev.sivakova.benchmark.samples.JMHSample_36_BranchPrediction
```
##### Результаты запуска JMHSample_36_BranchPrediction
```
Benchmark                               Mode  Cnt  Score   Error  Units
JMHSample_36_BranchPrediction.sorted    avgt   25  0,155 ? 0,002  ns/op
JMHSample_36_BranchPrediction.unsorted  avgt   25  3,011 ? 0,132  ns/op
```
#### JMHSample_37_CacheAccess.java
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar  dev.sivakova.benchmark.samples.JMHSample_37_CacheAccess
```
##### Результаты запуска JMHSample_37_CacheAccess
``` 
Benchmark                          Mode  Cnt   Score   Error  Units
JMHSample_37_CacheAccess.colFirst  avgt   25  11,650 ? 0,382  ns/op
JMHSample_37_CacheAccess.rowFirst  avgt   25   0,276 ? 0,012  ns/op
```
#### JMHSample_38_PerInvokeSetup.java
В папке `benchmark` выполните команду:
```
java -jar benchmark/target/benchmark-0.0.1-SNAPSHOT.jar  dev.sivakova.benchmark.samples.JMHSample_38_PerInvokeSetup
```
##### Результаты запуска JMHSample_38_PerInvokeSetup
```
Benchmark                          Mode  Cnt   Score   Error  Units
JMHSample_37_CacheAccess.colFirst  avgt   25  11,650 ? 0,382  ns/op
JMHSample_37_CacheAccess.rowFirst  avgt   25   0,276 ? 0,012  ns/op
```

