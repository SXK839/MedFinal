FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar


#Limit JVM memory usage (container-aware)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+UseG1GC"


EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]