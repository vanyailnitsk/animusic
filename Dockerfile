FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY database/migration database/migration
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
