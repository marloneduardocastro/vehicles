FROM openjdk:11-jdk-slim

EXPOSE 8080

VOLUME /tmp

RUN mkdir /app
RUN mkdir /config

COPY build/resources/main/application.yml config/application.yml
COPY build/libs/*.jar app/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]