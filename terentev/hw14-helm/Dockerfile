FROM openjdk:21-jdk

WORKDIR /app

COPY target/hw14-helm-*.jar app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "app.jar"]