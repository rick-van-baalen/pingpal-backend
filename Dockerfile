FROM eclipse-temurin:24-jdk-alpine
ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /app
COPY target/pingpal-backend-*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]

# Create the docker image --> docker build -t pingpal-db .
# Run the app --> docker-compose up --build