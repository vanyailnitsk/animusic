FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY --from=build /app/media-service/target/media-service-1.0-RELEASE.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
