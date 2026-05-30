FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar

#Optimize JVM for containers (memory + CPU efficient)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+UseG1GC"


ENTRYPOINT ["java", "-jar", "app.jar"]