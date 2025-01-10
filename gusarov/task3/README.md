# Spring boot приложение. (утечка памяти) 

### Описание:
* Swagger: http://127.0.0.1:8081/swagger-ui/index.html
* Добавлен метод (POST) http://localhost:8081/api/v1/users которому в теле передаётся login и password
* Логин уникальный, длина логина и пароля ограничена 100 символами.


### VM options:
-Xms64m -Xmx64m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
![opt_1.JPG](res/opt_1.JPG)

### Запуск с использованием jmeter (Файл приложен в папке res):
![jm_1.JPG](res/jm_1.JPG)
![jm_2.JPG](res/jm_2.JPG)
![jm_3.JPG](res/jm_3.JPG)


### Анализ дампа Eclipse Memory Analyzer Tool:
Видно 2 представителя на утечку:
![ema_1.JPG](res/ema_1.JPG)
![ema_2.JPG](res/ema_2.JPG)
![ema_3.JPG](res/ema_3.JPG)
![ema_4.JPG](res/ema_4.JPG)
После детального изучения видно что 31,5MB занял хешмап,  а 6,1MB оказалось springdoc (Swagger то я включил...) ( на принскринах выделены Shallow Heap, Retained Heap  и пути в приложении.) Видно что Хешмап находится в сервисе.
![ema_5.JPG](res/ema_5.JPG)

### После изменения кеша на SoftReference утечка перестала происходить!

