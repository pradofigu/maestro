FROM openjdk:17-jdk-slim

WORKDIR /maestro

COPY build/libs/maestro.jar maestro.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "maestro.jar"]
