import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.flywaydb.flyway") version "9.8.1"
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("br.com.caelum.stella:caelum-stella-core:2.1.6")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.0")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework:spring-jdbc")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:r2dbc-postgresql")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")

}

val properties = Properties().apply {
    val propertiesFile = when(System.getProperty("spring.profiles.active") ?: "dev") {
        "test" -> "src/test/resources/application-test.properties"
        else -> "src/main/resources/application.properties"
    }

    load(rootProject.file(propertiesFile).reader())
}

flyway {
    val dbUrl = System.getenv("SPRING_DATASOURCE_USERNAME") ?: "jdbc:postgresql://localhost:5432/maestro"
    val dbUsername = System.getenv("SPRING_DATASOURCE_USERNAME") ?: "admin"
    val dbPassword = System.getenv("SPRING_DATASOURCE_PASSWORD") ?: "admin"

    schemas = arrayOf(properties.getProperty("db.schema"))
    url = dbUrl
    user = dbUsername
    password = dbPassword
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
}