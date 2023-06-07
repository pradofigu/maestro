package br.com.pradofigu.maestro.output.database.config

import br.com.pradofigu.maestro.domain.config.ConfigService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["br.com.pradofigu.maestro.output.database"])
class PersistenceContextDevelopment(private val config: ConfigService) {

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory) = JpaTransactionManager().apply {
        this.entityManagerFactory = entityManagerFactory
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val jpaProperties = Properties().apply {
            put("hibernate.dialect", config.getProperty("spring.jpa.properties.hibernate.dialect"))
            put("hibernate.hbm2ddl.auto", config.getString("spring.jpa.hibernate.ddl-auto", "create"))
            put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor")
            put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy")
            put("hibernate.show_sql", "false")
            put("hibernate.format_sql", "false")
            put("hibernate.temp.use_jdbc_metadata_defaults", "false")

            put("hibernate.jdbc.batch_size", "30")
            put("hibernate.order_inserts", "true")
            put("hibernate.order_updates", "true")
        }

        return LocalContainerEntityManagerFactoryBean().apply {
            this.dataSource = dataSource
            this.jpaVendorAdapter = HibernateJpaVendorAdapter()
            this.setPackagesToScan("br.com.guiabolso.chameleon")
            this.setJpaProperties(jpaProperties)
        }
    }

    @Bean
    fun getDataSource(): DataSource {
        val hikariConfig = HikariConfig().apply {
            this.jdbcUrl = config.getProperty("spring.datasource.url")
            this.driverClassName = config.getProperty("spring.datasource.driverClassName")
            this.username = config.getProperty("spring.datasource.username")
            this.password = config.getProperty("spring.datasource.password")

            this.minimumIdle = config.getInt("spring.datasource.hikari.minimum-idle", 1)
            this.maximumPoolSize = config.getInt("spring.datasource.hikari.maximum-pool-size", 15)

            this.connectionTestQuery = "SELECT 1"
        }

        return HikariDataSource(hikariConfig)
    }
}