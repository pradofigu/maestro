import nu.studer.gradle.jooq.JooqEdition
import org.flywaydb.gradle.task.FlywayMigrateTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property
import java.util.*

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.flywaydb.flyway") version "9.8.1"
    id("nu.studer.jooq") version "8.2"
//    id("org.unbroken-dome.test-sets") version "2.1.1"
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
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")
    implementation("org.flywaydb:flyway-core")
//    implementation("org.springframework:spring-jdbc")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("org.postgresql:r2dbc-postgresql")

    jooqGenerator("org.postgresql:postgresql:42.5.1")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("com.h2database:h2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.security:spring-security-test")
}

val props = Properties().apply {
    val propertiesFile = when(System.getProperty("spring.profiles.active") ?: "dev") {
        "test" -> "src/test/resources/application-test.properties"
        else -> "src/main/resources/application.properties"
    }

    load(rootProject.file(propertiesFile).reader())
}

tasks {
    bootJar {
        archiveBaseName.set("maestro")
        archiveVersion.set("")
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    withType<Test> {
        systemProperty("spring.profiles.active", "test")
    }

    named<Test>("test") {
        useJUnitPlatform() {
            filter {
                setExcludePatterns("*IntegrationTest")
            }
        }
    }

    create("integrationTest", Test::class) {
        useJUnitPlatform() {
            filter {
                setIncludePatterns("*IntegrationTest")
            }
        }
    }

    named<FlywayMigrateTask>("flywayMigrate") {
        driver = props.getProperty("spring.datasource.driverClassName")
        url = props.getProperty("spring.datasource.url")
        user = props.getProperty("spring.datasource.username")
        password = props.getProperty("spring.datasource.password")
        baselineOnMigrate = true
        createSchemas = true
        defaultSchema = "flyway"
        schemas=arrayOf("flyway")
        table="schema_version"
        locations = arrayOf("filesystem:src/main/resources/db/migration")
    }
}

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = props.getProperty("spring.datasource.driverClassName")
                    url = props.getProperty("spring.datasource.url")
                    user = props.getProperty("spring.datasource.username")
                    password = props.getProperty("spring.datasource.password")
                    properties.add(Property().apply {
                        key = "ssl"
                        value = "false"
                    })
                }
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        includes = ".*"
                        excludes = "(?i:information_schema\\..*) | (?i:pg_catalog\\..*) | (?i:public\\..*)"
                    }
                    generate.apply {
                        isDeprecated = false
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "br.com.pradofigu.maestro"
                    }
                }
            }
        }
    }
}