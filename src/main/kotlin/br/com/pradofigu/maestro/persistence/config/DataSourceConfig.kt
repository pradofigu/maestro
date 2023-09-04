package br.com.pradofigu.maestro.persistence.config

import br.com.pradofigu.maestro.usecase.config.PropertiesConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource


@Configuration
class DataSourceConfig(private val propertiesConfig: PropertiesConfig) {

    @Bean
    fun dataSource(): DataSource = DriverManagerDataSource().apply {
        this.setDriverClassName("org.postgresql.Driver")
        this.url = propertiesConfig.getProperty("db.url") + propertiesConfig.getProperty("db.schema")
        this.username = propertiesConfig.getProperty("db.username")
        this.password = propertiesConfig.getProperty("db.password")
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val entityManager = LocalContainerEntityManagerFactoryBean()
        entityManager.setDataSource(dataSource)
        entityManager.setPackagesToScan("br.com.pradofigu.maestro.persistence") // set your entity classes package here
        entityManager.jpaVendorAdapter = HibernateJpaVendorAdapter()

        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "update")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
        properties.setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        entityManager.setJpaProperties(properties)

        return entityManager
    }
}