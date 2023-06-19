DOCKER_COMPOSE_FILE_PATH?="docker/docker-compose.yaml"

run:
	@./gradlew bootRun

compile:
	@echo "Compiling project and Generating database entity classes with JOOQ"
	@./gradlew build -x test

build: migrate compile

migrate:
	@echo "Starting migration"
	@./gradlew flywayMigrate

# INFRASTRUCTURE
start:
	@echo "Starting containers..."
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d

stop:
	@echo "Stopping containers..."
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} stop

kill: stop
	@echo "Removing containers..."
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} rm -f -v

clean: kill
	@echo "Deleting named volumes..."
	@docker volume rm docker_maestro-postgres-data
	@docker volume rm docker_maestro-pgadmin4-data