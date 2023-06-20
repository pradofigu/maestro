import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property
import java.util.*

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.flywaydb.flyway") version "9.8.1"
    id("nu.studer.jooq") version "8.2"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
}

group = "br.com.pradofigu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("br.com.caelum.stella:caelum-stella-core:2.1.6")
//    implementation("org.springframework:spring-jdbc")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("org.postgresql:r2dbc-postgresql")
    jooqGenerator("org.postgresql:postgresql:42.5.1")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
//    testImplementation("org.springframework.security:spring-security-test")
}

val props = Properties().apply {
    load(rootProject.file("src/main/resources/application.properties").reader())
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<org.flywaydb.gradle.task.FlywayMigrateTask>("flywayMigrate") {
    driver = props.getProperty("datasource.driver")
    url = props.getProperty("datasource.jdbc-url")
    user = props.getProperty("datasource.username")
    password = props.getProperty("datasource.password")
    baselineOnMigrate = true
    createSchemas = true
    defaultSchema = "flyway"
    schemas=arrayOf("flyway")
    table="schema_version"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

jooq {
    version.set(dependencyManagement.importedProperties["jooq.version"])  // default (can be omitted)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // default (can be omitted)

    configurations {
        create("main") {  // name of the jOOQ configuration
            generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = props.getProperty("datasource.driver")
                    url = props.getProperty("datasource.jdbc-url")
                    user = props.getProperty("datasource.username")
                    password = props.getProperty("datasource.password")
                    properties.add(Property().apply {
                        key = "ssl"
                        value = "false"
                    })
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        includes = ".*"
                        excludes = "(?i:information_schema\\..*) | (?i:pg_catalog\\..*) | (?i:public\\..*)"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isPojos = false
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "br.com.pradofigo.maestro.infrastructure.entities"
                        directory = "build/generated-sources/jooq/main"  // default (can be omitted)
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}