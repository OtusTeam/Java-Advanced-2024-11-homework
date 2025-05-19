FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY target/task15-0.0.1-SNAPSHOT.jar /opt/app/app.jar
EXPOSE 80
CMD ["java", "-jar", "/opt/app/app.jar"]