# Build stage
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY . .

# Build the application
RUN mvn clean package -Dmaven.test.skip=true

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built jar file from the build stage
# Assuming the main jar is in terra-ems-admin
COPY --from=build /app/terra-ems-admin/target/terra-ems-admin-*.jar app.jar

# Set environment variables with defaults
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application with explicit memory limits for Render free tier (512MB RAM)
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]
