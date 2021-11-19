#!/usr/bin/env bash

echo "Building the application"
./gradlew clean build

echo "Running the application"
./gradlew bootrun
