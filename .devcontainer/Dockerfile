# Use a supported Dev Container image for Java 17
FROM mcr.microsoft.com/devcontainers/java:0-17

# Install Maven and Ant
RUN apt-get update && apt-get install -y maven ant && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /workspace
