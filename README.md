# maestro
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

2. Set up the project infrastructure creating the docker containers
    - Execute the `make start` command
    - (Optional) Execute the `make stop` command - To stop the containers 

3. Now you have 3 options to work on. You can:
   1. Only migrate the script with flyway executing the `make migrate` command
   2. Compile the project to generate JOOQ entities executing the `make compile` command
      - Check the `build/generated-sources/jooq` folder to see the database record entities generated
   3. Steps 1 and 2 in a row executing the `make build` command

4. Run the Application:
    - Execute the `make run` command

## Application

- **Host**: http://localhost:8080

- **Swagger API**: http://localhost:8080/swagger-ui

- **PgAdmin**: http://localhost:5050/login
    - **use**r: admin@admin.com
    - **password**: admin *(the same to connect to the database)*
