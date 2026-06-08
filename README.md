# Healthcare Microservices System

# Overview
This project is a microservices-based healthcare system built using multiple independent services. Each service handles a specific domain and communicates via APIs.

# Architecture
- Auth Service – Handles authentication and authorization
- Patient Service – Manages patient data
- Risk Assessment Service – Calculates and evaluates health risks
- Gateway Service – API Gateway routing requests to services
- Frontend Service – UI layer for user interaction

# Project Structure
root/
├── auth-service/
├── patient-service/
├── risk-assessment-service/
├── gateway-service/
├── front-end-service/
├── docker-compose.yml
├── pom.xml
└── README.md

# Technologies Used
- Java / Spring Boot
- Maven
- Docker & Docker Compose
- REST APIs
- Microservices Architecture

# Getting Started
Prerequisites:
- Java 17+
- Maven
- Docker & Docker Compose

# Run the Application:
1. Clone the repo
2. cd into project folder

# Start services:
docker-compose up --build

# Stop services:
docker-compose down

# Build Project:
mvn clean install

# Testing:
mvn test

# Common Issues:
- Port already in use: Change ports in docker-compose.yml
- Docker not running: Start Docker Desktop

  ## Deployment

- Can be deployed using Docker containers
- Compatible with cloud platforms such as Azure and AWS

## Best Practices (Green Code)

- Reduce unnecessary logging to improve performance
- Use efficient database queries
- Enable lazy initialization
- Optimize Docker containers with resource limits
- Minimize payload size for faster network operations

Author:
Sridhar R Kankanala
