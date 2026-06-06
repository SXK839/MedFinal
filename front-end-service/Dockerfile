FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar
COPY target/*.jar app.jar

# JVM tuning (good for containers ✅)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+UseG1GC"

# Expose port
EXPOSE 8085

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]