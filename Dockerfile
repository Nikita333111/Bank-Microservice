FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/bankservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]