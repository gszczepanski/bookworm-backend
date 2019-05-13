CREATE ROLE bookworm_user WITH PASSWORD 'xyzXYZxyz';
ALTER ROLE bookworm_user WITH LOGIN;

CREATE DATABASE bookworm_library OWNER bookworm_user;
