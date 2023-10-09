#!/bin/bash

# Step 1: Create the Build
./gradlew build

# Step 2: Build Docker Images and Create Containers
docker-compose build

# Step 3: Start Containers
docker-compose up