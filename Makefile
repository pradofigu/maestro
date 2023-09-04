DOCKER_COMPOSE_FILE_PATH="docker-compose.yaml"

run: ## Runs the application through Gradle bootRun task
	@./gradlew bootRun

# COMPILING

compile: ## Cleans the project and compiles it
	@echo "-------------- Compiling project and Generating database entity classes with JOOQ -------------- "
	@./gradlew clean build -x test

migrate: ## Runs Flyway migrations (it also generates the database entity classes with JOOQ)
	@echo "-------------- Starting migration --------------"
	@./gradlew flywayMigrate
	@echo "-------------- Finish migration --------------"

rebuild-migrations: clean start-db migrate ## Stops and removes all containers, starts the database container and runs Flyway migrations

build: migrate compile ## Runs Flyway migrations and compiles the project

# TESTING

test: ## Runs unit tests
	@echo "-------------- Starting Unit Tests--------------"
	-@./gradlew test
	@echo "-------------- Finish Unit Tests--------------"

integration-test: start-db migrate ## Runs integration tests
	@echo "-------------- Starting Integration Tests--------------"
	-@./gradlew integrationTest --stacktrace
	@echo "-------------- Finish Integration Tests--------------"

all-tests: test integration-test ## Runs all tests

test-rebuild: kill integration-test ## Stops and removes all containers, starts the database container and runs integration tests

# INFRASTRUCTURE
start-db: ## Starts the database container (postgres and pgadmin)
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d database pgadmin

start-app: ## Starts the application container
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d app

start: ## Starts all containers but without running Flyway migrations or compiling the application
	@echo "-------------- Starting Containers --------------"
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d

stop: ## Stops all containers (application, database, pgadmin)
	@echo "-------------- Stopping Containers --------------"
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} stop

kill: stop ## Stops and removes all containers (application, database, pgadmin)
	@echo "-------------- Removing Containers --------------"
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} rm -f -v

clean: kill ## Stops and removes all containers (application, database, pgadmin) and deletes all volumes
	@echo "-------------- Deleting Named volumes --------------"
	@docker volume rm maestro_maestro-postgres-data
	@docker volume rm maestro_maestro-pgadmin4-data

# Necessary order to run migrations from Flyway before compiling the application
# (JOOQ classes must be generated with the migrations already applied)
up: start-db migrate compile start-app ## Starts all containers, runs Flyway migrations and compiles the application

help: ## Show this help
	@echo 'Uso: make [TARGET]'
	@echo 'Targets:'
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-15s\033[0m %s\n", $$1, $$2}'
