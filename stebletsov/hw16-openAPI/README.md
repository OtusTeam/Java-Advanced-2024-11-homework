# Домашнее задание №16

## Описать Rest сервис с помощью OpenAPI

Собираем package
```shell
mvn clean package
```

Запускам docker compose.

JSON-документация:
http://localhost:80/api-docs

![OPENAPI_1.png](src%2Fmain%2Fresources%2FOPENAPI_1.png)

Swagger UI:
http://localhost:80/api-docs-ui

![OPENAPI_2.png](src%2Fmain%2Fresources%2FOPENAPI_2.png)
![OPENAPI_3.png](src%2Fmain%2Fresources%2FOPENAPI_3.png)
![OPENAPI_4.png](src%2Fmain%2Fresources%2FOPENAPI_4.png)

Регистрируем пользователя через Swagger 
![OPENAPI_5.png](src%2Fmain%2Fresources%2FOPENAPI_5.png)

Смотрим emails через Swagger:
![OPENAPI_6.png](src%2Fmain%2Fresources%2FOPENAPI_6.png)


## Цель:

* Подключить springdoc в проект
* Описать DTO и параметры эндпойтов аннотациями из OpenAPI (@Schema, @Parameter)
* Настроить эндпойнт для OpenAPI по адресу /api-docs
* Подключить SwaggerAPI и замапать его на /api-docs-ui

## Описание/Пошаговая инструкция выполнения домашнего задания:

* Подключить springdoc в проект
* Описать DTO и параметры эндпойтов аннотациями из OpenAPI (@Schema, @Parameter)
* Настроить эндпойнт для OpenAPI по адресу /api-docs
* Подключить SwaggerAPI и замапать его на /api-docs-ui

