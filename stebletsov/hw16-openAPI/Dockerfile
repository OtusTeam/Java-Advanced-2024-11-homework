FROM openjdk:21-slim
WORKDIR /app
COPY target/hw16-openAPI.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
