# Домашнее задание №14

## Запуск SpringBoot приложения в Kubernetes с помощью Helm

## Цель: Создать SpringBoot приложение с эндпоинтми и запустить его в Kubernetes с помощью Helm

Собираем package и docker образ
```shell
mvn clean package
```

Проверяем доступность локального кластера:

```shell
kubectl get nodes
```

![HELM_1.png](src%2Fmain%2Fresources%2FHELM_1.png)

Пушим образ в свой репозиторий:
```shell
docker tag hw14-helm-app:latest mortar89/hw14-helm-app:latest
docker push mortar89/hw14-helm-app:latest
```

![HELM_2.png](src%2Fmain%2Fresources%2FHELM_2.png)

Создаем чарт:

```shell
helm create hw14-chart
```
Лишние файлы были удалены.

Устанавливаем chart, смотрим есть ли под
```shell
helm install hw14-stebletsov ./hw14-chart -f hw14_values.yaml
kubectl get pods
```
![HELM_3.png](src%2Fmain%2Fresources%2FHELM_3.png)


Смотрим внешний ip
```shell
kubectl get svc
```
![HELM_4.png](src%2Fmain%2Fresources%2FHELM_4.png)

Выполянем запросы из requests.http, смотрим логи kubernetes
![HELM_5.png](src%2Fmain%2Fresources%2FHELM_5.png)
![HELM_6.png](src%2Fmain%2Fresources%2FHELM_6.png)

## Описание/Пошаговая инструкция выполнения домашнего задания:

* используем готовое приложение с домашнего задания "Вспоминаем Docker"
* необходимо написать Chart, который будет шаблонизировать объекты Deployment Service
* заполнить values.yaml для приложения
* осуществить деплой в кластер kubernetes (локальный или yandex cloud)
* получить ответ о своих эндпоинтов
