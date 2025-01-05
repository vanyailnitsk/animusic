FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY dependencies.gradle dependencies.gradle
COPY src src
COPY .env .env

RUN chmod +x gradlew

RUN ./gradlew build -x test

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/animusic-1.0.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
