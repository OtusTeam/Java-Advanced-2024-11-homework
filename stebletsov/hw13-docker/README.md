# Домашнее задание №13

## Запуск SpringBoot приложения в Docker

## Цель: Создать SpringBoot приложение с эндпоинтами и запустить его в Docker

Собираем jar:
```shell
mvn clean package
```

Собираем docker образ и cтартуем его или стартуем из Idea:
```shell
docker-compose up --build
```

![DOCKER_1.png](src/main/resources/DOCKER_1.png)

Проверяем доступен ли сервис из контейнера:
![DOCKER_2.png](src/main/resources/DOCKER_2.png)

В логах контейнера видим обработанный запрос:
![DOCKER_3.png](src/main/resources/DOCKER_3.png)

Получение списка пользователей:
![DOCKER_4.png](src/main/resources/DOCKER_4.png)

Проверям опять в логах контейнера обработанный запрос:
![DOCKER_5.png](src/main/resources/DOCKER_5.png)

## Описание/Пошаговая инструкция выполнения домашнего задания:

* Добавить в Spring приложение новый энпойнт - GET /hello, который будет возвращать строку "Hello world!"
* Собрать JAR файл своего приложенния
* Создать docker образ на основе образа с JDK:
* Образ должен быть в формате Dockerfile
* При запуске контейнера, должно запускать приложение
* Энпойнты должны быть доступны по порту 80
