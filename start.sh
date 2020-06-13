#!/bin/sh

cd frontend/
npm run serve &
cd ../backend
./gradlew bootJar
cd ..
java -jar backend/build/libs/backend-0.0.1-SNAPSHOT.jar


