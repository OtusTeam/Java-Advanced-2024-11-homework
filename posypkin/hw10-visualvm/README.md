Домашнее задание на тему: Профилирование с помощью visualvm

Запуск от java 17 и выше

1. Добавляем ошибки [сюда](src/main/java/ru/otus/service/RegistrationServiceImpl.java)
2. Запускаем сервис, файл для Jmetre лежит в resource.
Даем нагрузку с помощью JMetre
3. Смотрим через visualvm, здесь мы видим что отправляются странные запросы на удаление пользователей
![img.png](img.png)
4. далее мы видим в стэк трэйсе от куда этот запрос отправляется
![img_1.png](img_1.png)
5. в sampler мы фильтруем вызовы только наших методов
![img_2.png](img_2.png)
6. и здесь видим exception и вызов delete all
![img_3.png](img_3.png)
7. у меня бесплатная версия IDEA поэтому не могу запустить asyncprofiler