# Запуск SpringBoot приложения в Docker

## Цель
Создать SpringBoot приложение с эндпойнтами и запустить его в Docker

## Инструкция
1. Собрать JAR:
```
mvn clean package
```
2. Сбилдить docker-образ:
```
docker build -t docker-spring-app .
```
3. Запустить контейнер:
```
docker run -p 80:8080 docker-spring-app
```