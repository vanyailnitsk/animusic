FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /app
COPY media-service/pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:18
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
