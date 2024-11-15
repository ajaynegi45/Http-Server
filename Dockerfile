FROM openjdk:17-jdk-slim

LABEL authors="ajaynegi"

# Set the working directory inside the container
WORKDIR /app

# Copy the HTTP server JAR file into the container
COPY target/http-server.jar /app/http-server.jar

# Expose the port your HTTP server listens on
EXPOSE 8080

# Define the entry point to run the server
ENTRYPOINT ["java", "-jar", "http-server.jar"]


# mvn clean package
# docker build -t http-server .
# docker run -d -p 8080:8080 --name http-server-container http-server



## Step 1: Build stage
#FROM maven:3.8.7-openjdk-17 AS builder
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package


## Step 2: Runtime stage
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=builder /app/target/http-server.jar /app/http-server.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "http-server.jar"]
