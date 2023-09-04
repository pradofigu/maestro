package br.com.pradofigu.maestro.persistence.config

import br.com.pradofigu.maestro.usecase.config.PropertiesConfig
import org.flywaydb.core.Flyway

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class FlywayConfig(private val propertiesConfig: PropertiesConfig) {

    @Bean
    fun flyway(dataSource: DataSource): Flyway = Flyway.configure()
        .dataSource(dataSource)
        .baselineOnMigrate(true)
//        .createSchemas(true)
//        .defaultSchema(propertiesConfig.getProperty("db.schema"))
//        .table("flyway_schema_history")
        .locations("classpath:db/migration")
        .load()
        .also { it.migrate() }
}