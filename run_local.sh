#!/bin/bash

# SelfSens Run Local Script

echo "🚀 Starting SelfSens Project Setup..."

# Ensure permissions
chmod +x mvnw

# Export Maven User Home to local directory to avoid permission issues
export MAVEN_USER_HOME="./.m2"

echo "📦 Building project..."
./mvnw clean compile

echo "🏃 Running Spring Boot application..."
./mvnw spring-boot:run
