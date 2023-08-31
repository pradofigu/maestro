#!/bin/bash

apt update

apt install snapd



# Variáveis de ambiente
DB_URL=jdbc:postgresql://svc-postgres:5432/maestro
DB_USER=admin
DB_PASSWORD=admin

# Diretório de migração
MIGRATION_DIR=/maestro/db/migration

# Caminho para o Flyway 
FLYWAY_CMD=/maestro/flyway/flyway

# Aplicar migrações
$FLYWAY_CMD -url=$DB_URL -user=$DB_USER -password=$DB_PASSWORD -locations=filesystem:$MIGRATION_DIR migrate

# Iniciar o aplicativo Kotlin
java -Dspring.profiles.active=prod -jar maestro.jar
