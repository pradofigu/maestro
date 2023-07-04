DOCKER_COMPOSE_FILE_PATH?="docker/docker-compose.yaml"

run:
	@./gradlew bootRun

# COMPILING

compile:
	@echo "-------------- Compiling project and Generating database entity classes with JOOQ -------------- "
	@./gradlew clean build -x test

migrate:
	@echo "-------------- Starting migration --------------"
	@./gradlew flywayMigrate
	@echo "-------------- Finish migration --------------"

# TESTING

test:
	@echo "-------------- Starting Unit Tests--------------"
	-@./gradlew test
	@echo "-------------- Finish Unit Tests--------------"

integration-test:
	@echo "-------------- Starting Integration Tests--------------"
	-@./gradlew integrationTest
	@echo "-------------- Finish Integration Tests--------------"

all-tests: test integration-test

# INFRASTRUCTURE
start-db:
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d database pgadmin

start-app:
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d app

start:
	@echo "-------------- Starting Containers --------------"
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} up -d

stop:
	@echo "-------------- Stopping Containers --------------"
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} stop

kill: stop
	@echo "-------------- Removing Containers --------------"
	@docker-compose -f ${DOCKER_COMPOSE_FILE_PATH} rm -f -v

clean: kill
	@echo "-------------- Deleting Named volumes --------------"
	@docker volume rm docker_maestro-postgres-data
	@docker volume rm docker_maestro-pgadmin4-data

build: start-db migrate compile start-app