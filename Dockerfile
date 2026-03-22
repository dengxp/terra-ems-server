# Terra EMS Server — 使用本地编译的 jar
# 先运行 build.sh 再 docker compose up

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY ../build/terra-ems-server.jar app.jar

RUN mkdir -p /app/logs

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-Xms128m", "-Xmx512m", "-jar", "app.jar"]
