FROM alpine:latest

RUN mkdir /bookworm && mkdir /volume

COPY keycloak-db-init.sql /bookworm
COPY realm-bookworm.json /bookworm

