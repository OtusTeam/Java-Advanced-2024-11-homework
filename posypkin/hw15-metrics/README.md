Домашнее задание на тему: Prometheus & Grafana

Запуск
````shell
docker-compose -f infra/docker-compose-metrics.yml -p otus up -d
````

Остановка
````shell
docker-compose -f infra/docker-compose-metrics.yml -p otus down 
````

![img_2.png](img_2.png)

![img_3.png](img_3.png)

RPS, RPM и error
![img.png](img.png)
