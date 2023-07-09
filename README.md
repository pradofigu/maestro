# Maestro
Fast food monolith service

## Software Architecture

-  [Kotlin](https://kotlinlang.org/)
-  [Spring](https://spring.io/)
-  [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
-  [R2DBC](https://r2dbc.io/)
-  [JOOQ](https://www.jooq.org/)
-  [Flyway](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
-  [JUnit](https://junit.org/junit5/)
-  [Mockk](https://mockk.io/)
-  [Swagger3](https://swagger.io/docs/specification/about/)

## Usage

1. Clone the repo

2. You have 2 options to work on. You can: 
- Run the application and databases inside docker containers 

```bash
$ make up
```

- Run the application using `make` commands (or debugging)
```bash
$ make start-db # Starts the database container and PgAdmin
$ make build # Compile the project to generate JOOQ entities
$ make run # Runs the application through bootRun task (alternatively you can run the application through IntelliJ

# PS: Check the `build/generated-sources/jooq` folder to see the database record entities JOOQ generated
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
- **Maestro Ubiquitous Language**: [Documentation](docs/Glossário%20de%20Linguagem%20Ubiqua.md)