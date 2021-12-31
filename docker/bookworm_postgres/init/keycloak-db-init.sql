CREATE USER bookworm_keycloak_user WITH ENCRYPTED PASSWORD 'bookworm_keycloak_p';
CREATE DATABASE bookworm_keycloak;
GRANT ALL PRIVILEGES ON DATABASE bookworm_keycloak TO bookworm_keycloak_user;
