# Домашнее задание №4

## Анализ ускорения работы приложения при работе на GraalVM

Запуск версии на JVM занял почти ~4 секунды
```console
2025-05-05T17:04:29.044+03:00  INFO 27076 --- [task4] [main] dev.korolz.task4.Task4ApplicationTests   : Started Task4ApplicationTests in 3.665 seconds (process running for 4.595)
```
Запуск нативной версии занял ~0.3 секунды

```console
2025-05-05T17:12:23.113+03:00  INFO 4325 --- [task4] [main] dev.korolz.task4.Task4ApplicationTests   : Started Task4ApplicationTests in 0.278 seconds (process running for 3.535)
```
Для сборки нативного образа приложения, запустить:

```bash 
mvn native:build
```

## Цель:

Запустить ранее написанное приложение на GraalVM и оценить время старта и работы 
приложения (ускорение по отношению к обычной имплементации java)

## Описание/Пошаговая инструкция выполнения домашнего задания:

1. Реализовать простое приложение на Spring Boot 3 (из занятия Memory management. 
JVM memory structure)

2. Добавить плагин для сборки Native Image файлов

3. Выполнить сборку в Native Image

4. Запустить полученный файл и сравнить время запуска с запуском на JVM

5. Зафиксировать результаты (можно указать железо на котором выполнялся запуск)

6. Добавить простой unit тест и запустить nativeTestCompiler (Gradle)

7.* запустить файл в Docker


