# Build stage using Maven
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
# We skip tests to make deployment faster on Render's free tier
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
COPY --from=build /target/codeverse-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]