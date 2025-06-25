# Используем официальный образ OpenJDK 17
FROM openjdk:17

# Создаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY build/libs/hw15-metrics-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080 (внутри контейнера)
EXPOSE 8080

# Маппим порт 8080 на 80 при запуске контейнера
# Запускаем приложение
CMD ["java", "-jar", "app.jar"]