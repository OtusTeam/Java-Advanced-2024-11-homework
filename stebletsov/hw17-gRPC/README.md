# Домашнее задание №17

##  описать gRPC сервис

Собираем package
```shell
mvn clean package
```

Проверяем работу сервиса через requests.http
![GRPC_1.png](src/main/resources/GRPC_1.png)
![GRPC_2.png](src/main/resources/GRPC_2.png)
![GRPC_3.png](src/main/resources/GRPC_3.png)
![GRPC_4.png](src/main/resources/GRPC_4.png)
![GRPC_5.png](src/main/resources/GRPC_5.png)

Все запросы успешно выполнились:
![GRPC_6.png](src/main/resources/GRPC_6.png)

Проверяем логи сервера:
![GRPC_7.png](src/main/resources/GRPC_7.png)

## Цель:

*  Описать gRPC сервис и приложить скриншоты вывода консоли в пул реквест (вместе с рабочим
   кодом)

## Описание/Пошаговая инструкция выполнения домашнего задания:

* Подключить gRPC в проект и Реализовать сервис в Java
* Описать сущности при помощи protobuf:
    *  User (email, username, id),
    *  Product (name, id)
  
* Описать сервис при помощи protobuf:
    * createUser(email, username) returns id
    * changeUserEmail(id, email)
    * changeUserName(id, username)
    * createProduct(name) returns id
    * addProductToCart(userId, productId)

Критерии оценки:
* Все сущности и сервис реализованы правильно
* Проект должен собираться без ошибок а protobuf генерировать сущности и интерфейсы
* Есть скриншоты вывода консоли


