package br.com.pradofigu.maestro.persistence.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class FlywayConfig {

    @Bean
    fun flyway(dataSource: DataSource): Flyway = Flyway.configure()
        .dataSource(dataSource)
        .baselineOnMigrate(true)
        .locations("classpath:db/migration")
        .load()
        .also { it.migrate() }
}