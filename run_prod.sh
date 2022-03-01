#!/bin/bash
./gradlew clean
./gradlew bootJar --warning-mode all
cd docker || { echo "Docker folder does not exist."; exit 1; }
ln ../build/libs/bookworm-backend-0.0.1-SNAPSHOT.jar ./bookworm_backend/bookworm-backend-0.0.1-SNAPSHOT.jar
docker-compose up -d bookworm_postgres && ./wait-for-it.sh --timeout=10 docker:5432
docker-compose up -d bookworm_keycloak && ./wait-for-it.sh --timeout=10 docker:8080
docker-compose up --build
unlink ./bookworm_backend/bookworm-backend-0.0.1-SNAPSHOT.jar
