DOCKER_COMPOSE_FILE_PATH="docker-compose.yaml"

run: ## Runs the application through Gradle bootRun task
	@./gradlew bootRun

# COMPILING

compile: ## Cleans the project and compiles it
	@echo "-------------- Compiling project -------------- "
	@./gradlew clean build -x test

migrate: ## Runs Flyway migrations
	@echo "-------------- Starting migration --------------"
	@./gradlew flywayMigrate
	@echo "-------------- Finish migration --------------"

run: ## Runs the app through Gradle bootRun task
	@echo "-------------- Running app --------------"
	@./gradlew bootRun

rebuild-migrations: clean start-db migrate ## Stops and removes all containers, starts the database container and runs Flyway migrations

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

help: ## Show this help
	@echo 'Uso: make [TARGET]'
	@echo 'Targets:'
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-15s\033[0m %s\n", $$1, $$2}'
