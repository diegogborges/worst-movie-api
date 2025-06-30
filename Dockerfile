FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

RUN apk add --no-cache maven

COPY /src /app/src
COPY /pom.xml /app

RUN mvn clean package --file /app/pom.xml -DskipTests

FROM eclipse-temurin:21-jdk-alpine

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
