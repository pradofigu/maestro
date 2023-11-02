FROM openjdk:17-jdk-slim

ENV SPRING_DATASOURCE_URL jdbc:postgresql://database:5432/maestro
ENV SPRING_DATASOURCE_USERNAME admin
ENV SPRING_DATASOURCE_PASSWORD admin

WORKDIR /maestro

COPY build/libs/maestro.jar maestro.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "maestro.jar"]
