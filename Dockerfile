FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

CMD ["java", "-jar", "/app/app.jar"]
