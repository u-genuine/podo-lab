#!/bin/bash
./gradlew clean build -x test && docker compose -f docker-compose.yml -f docker-compose.local.yml up -d --build