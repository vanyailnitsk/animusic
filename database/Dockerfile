FROM postgres:14.5

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB postgres

COPY backup.sql ./docker-entrypoint-initdb.d/