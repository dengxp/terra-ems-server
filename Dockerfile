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

# Expose standard port for local dev
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
