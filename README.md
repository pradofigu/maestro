# Maestro
Fast food monolith service

## Software Architecture

-  [Kotlin](https://kotlinlang.org/)
-  [Spring](https://spring.io/)
-  [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
-  [Flyway](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
-  [JUnit](https://junit.org/junit5/)
-  [Swagger3](https://swagger.io/docs/specification/about/)

## Usage

1. Clone the repo

2. You have 2 options to work on. You can: 
- Run the application and databases inside docker containers 

```bash
$ make start
```

- Run the application using `make` commands (or debugging)
```bash
$ make start-db # Starts the database container and PgAdmin
$ make run # Starts the application with Gradle task
```

## Application

- **Host**: http://localhost:8080
- **Swagger API**: http://localhost:8080/swagger-ui
- **PgAdmin**: http://localhost:5050/login
    - **user**: admin@admin.com
    - **password**: admin *(the same to connect to the database)*

Use `make help` to see all available commands.

## Documentation

- **Event Storming Project Board**: https://miro.com/app/board/uXjVMDHdGWk=/
- **Maestro Ubiquitous Language**: [Documentation](documentation/event-storming/Glossário%20de%20Linguagem%20Ubiqua.md)