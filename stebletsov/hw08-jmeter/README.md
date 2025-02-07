# Домашнее задание №8

## Реализовать генератор нагрузки

## Используемые параметры

- Количество потоков: 100
- Время разогрева: 5 сек
- Количество циклов: 10 
- Сгенерированный скрипт Jmeter через UI: Thread Group for HW08.jmx
- Сгенерированный скрипт Jmeter программно: script.jmx

## Jmeter UI
![ui_1.png](hw08-jmeter-runner/src/main/resources/ui_1.png)
![ui_2.png](hw08-jmeter-runner/src/main/resources/ui_2.png)
![ui_3.png](hw08-jmeter-runner/src/main/resources/ui_3.png)
![ui_4.png](hw08-jmeter-runner/src/main/resources/ui_4.png)
![ui_5.png](hw08-jmeter-runner/src/main/resources/ui_5.png)
![ui_6.png](hw08-jmeter-runner/src/main/resources/ui_6.png)

## Jmeter Runner
![code_jmeter.png](hw08-jmeter-runner/src/main/resources/code_jmeter.png)

## Цель: Сделать генератор нагрузки для выбранного протокола

## Описание/Пошаговая инструкция выполнения домашнего задания:

Для выполнения задания потребуется сервис регистрации пользователя, реализованный ранее.

Создать тестовый план с регулиремым RPS.

- Сделать тесты через UI-ный интерфейс Jmetera

- Сделать тесты через отдельный подмодуль с библиотекой Jmeter.

При старте данного подмодуля jar должен запускаться и генерировать нагрузку на основное приложение