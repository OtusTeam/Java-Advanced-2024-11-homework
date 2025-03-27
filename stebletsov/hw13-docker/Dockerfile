FROM openjdk:21-slim
WORKDIR /app
COPY target/hw13-docker.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
