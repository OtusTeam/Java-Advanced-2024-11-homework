# Домашнее задание №2

## Оптимизация SerialGC

Для запуска:
```console 
mvn clean package
```

```bash 
java -XX:+UseSerialGC -Xms128m -Xmx128m -Xlog:gc*::time -XX:SurvivorRatio=1 -XX:NewRatio=1 -cp target/classes ru.otus.Practice > gc.log
```

## Параметры JVM: -XX:SurvivorRatio=1 -XX:NewRatio=1

## Цель:

Данная работа позволит закрепить изученный материал по работе с параметрами GC
и с инструментами мониторинга работы GC

## Описание/Пошаговая инструкция выполнения домашнего задания:

1. Скачать visualVm ( https://visualvm.github.io/)
2. Запустить visualVm
3. Установить плагин Visual GC для visualVm
4. Создать проект Practice (или скачать с репозитория https://github.com/OtusTeam/Java-Advanced.git java-memory/gc_serial_parallel_cms )
5. Установить параметры запуска приложения -XX:+UseSerialGC -Xms128m -Xmx128m -Xlog:gc*::time
6. Запустить приложение
7. В логах найти хотя бы одно Pause Full
8. Настроить -XX:SurvivorRatio=? или/и -XX:NewRatio=? Так что бы приложение не запускало полную сборку до конца своего выполнения



