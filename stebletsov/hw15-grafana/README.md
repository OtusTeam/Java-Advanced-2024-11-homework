# Домашнее задание №15

## Создать метрики для отслеживания нагрузки на Rest сервис

## Цель: Покрыть Rest сервис метриками, построить дашборд к Grafana и продемонстрировать результаты дашборда, подавая нагрузку через JMeter

Собираем package
```shell
mvn clean package
```

Далее нужно запустить docker compose.

На приложение подавалась нагрузка через jmeter, в ресурсах лежит конфигурация 

Пользователь/пароль для grafana/prometheus:
* admin
* pass

Метрики:
http://localhost:80/actuator/prometheus
![Prometheus.png](src%2Fmain%2Fresources%2FPrometheus.png)

Grafana:
http://localhost:3000

Заходим в Dashboards->HW15 Monitoring (дашборд создается через provision)
![Dashboard.png](src%2Fmain%2Fresources%2FDashboard.png)

Каждая метрика во view режиме:
![Latency.png](src%2Fmain%2Fresources%2FLatency.png)
![RPS.png](src%2Fmain%2Fresources%2FRPS.png)
![RPM.png](src%2Fmain%2Fresources%2FRPM.png)
![Errors.png](src%2Fmain%2Fresources%2FErrors.png)
![RAM.png](src%2Fmain%2Fresources%2FRAM.png)
![CPU.png](src%2Fmain%2Fresources%2FCPU.png)


## Описание/Пошаговая инструкция выполнения домашнего задания:

* Используя Actuator+Prometheus+Grafana построить дашбор к Grafana.
* На дашборде должны присутствоать графики:
* latency - время выполнения HTTP запросов на каждый endpoint приложения
* traffic - объем трафика, который обрабатываете прямо сейчас, это RPS (request per seconds), RPM (request per minute)
* errors - количество ошибок, некорректные коды ответа.
* saturation - загруженность приложения RAM и CPU.
