# Use the official OpenJDK 17 image as a base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application's JAR file (adjust the name if necessary)
COPY  target/Kelvin_Accounting_API-0.0.1-SNAPSHOT.jar /app/Kelvin_Accounting_API-0.0.1-SNAPSHOT.jar
COPY resources/calibri-font-family /app/resources/calibri-font-family

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/Kelvin_Accounting_API-0.0.1-SNAPSHOT.jar"]
