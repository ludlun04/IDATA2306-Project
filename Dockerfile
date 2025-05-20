FROM openjdk:23-jdk-slim

COPY ./target/idata2306-project-1.0.jar /app.jar
EXPOSE 8080

ENTRYPOINT [ "java", "-Dspring.config.location=classpath:/application-prod.properties", "-jar", "/app.jar" ]
