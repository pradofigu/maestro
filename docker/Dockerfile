FROM openjdk:17-jdk-slim

ENV APP_DIR=/maestro

RUN mkdir -p $APP_DIR && cd $APP_DIR && ls -la

COPY $APP_DIR/build/libs/maestro.jar maestro.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "maestro.jar"]
