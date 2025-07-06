# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .
RUN  mvn -pl task13 -am clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/task13/target/*.jar task13.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "task13.jar"]

