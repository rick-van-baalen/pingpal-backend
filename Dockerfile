FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY target/pingpal-backend-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]