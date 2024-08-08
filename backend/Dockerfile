FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/api/target/api-1.0.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=dev"]
