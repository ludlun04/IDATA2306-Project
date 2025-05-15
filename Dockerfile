FROM openjdk:23-jdk-slim

COPY ./target/idata2306-project-0.1.jar /app.jar
EXPOSE 8080

ENTRYPOINT [ "java", "-Dspring.config.location=classpath:/application-prod.properties", "-Dspring.config.name=", "-jar", "/app.jar" ]
