FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY target/pingpal-backend-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Create the docker image --> docker build -t pingpal-backend .
# Run the app --> docker-compose up --build