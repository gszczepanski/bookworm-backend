#!/bin/bash
./gradlew bootJar --warning-mode all
cd docker || { echo "Docker folder does not exist."; exit 1; }
cp -f ../build/libs/bookworm-backend-0.0.1-SNAPSHOT.jar ./bookworm_backend/
cd bookworm_backend
docker-compose up --build
rm bookworm-backend-0.0.1-SNAPSHOT.jar
