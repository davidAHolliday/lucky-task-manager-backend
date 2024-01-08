# Use the official OpenJDK base image with JDK 17
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build/libs directory to the container
COPY build/libs/luckydashboard-0.0.1-SNAPSHOT.jar /app/app.jar

# Specify the default command to run your Spring Boot application
CMD ["java", "-jar", "/app/app.jar"]

# Expose the port your Spring Boot application runs on (default port is 8080)
EXPOSE 8080
