#!/bin/bash
./gradlew bootJar --warning-mode all
cd docker || { echo "Docker folder does not exist."; exit 1; }
ln ../build/libs/bookworm-backend-0.0.1-SNAPSHOT.jar ./bookworm_backend/bookworm-backend-0.0.1-SNAPSHOT.jar
cd bookworm_backend
docker-compose up --build
unlink bookworm-backend-0.0.1-SNAPSHOT.jar
