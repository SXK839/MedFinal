FROM eclipse-temurin:17-jdk-alpine


# ✅ Set working directory
WORKDIR /app

# ✅ Copy only jar (keeps image small)
COPY target/*.jar app.jar

# ✅ JVM tuned for containers (green optimization)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+UseG1GC"
