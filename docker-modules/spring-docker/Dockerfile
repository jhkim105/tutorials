#FROM eclipse-temurin:17-jdk-alpine
FROM amazoncorretto:17
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","application.jar"]