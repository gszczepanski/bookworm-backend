#!/bin/bash
# script to run secondary services without bookworm_backend for development purposes
cd docker || { echo "Docker folder does not exist."; exit 1; }
docker-compose --env-file .env.dev up bookworm_postgres bookworm_pgadmin bookworm_keycloak bookworm_redis
