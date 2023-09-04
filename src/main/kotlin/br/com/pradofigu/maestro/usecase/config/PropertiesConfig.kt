package br.com.pradofigu.maestro.usecase.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class PropertiesConfig(private val env: Environment) {

    @Throws(IllegalArgumentException::class)
    fun getProperty(property: String): String {
        return env.getProperty(property)
            ?: throw IllegalArgumentException("Configuration property $property not found.")
    }

    fun getProperty(property: String, defaultValue: String): String {
        return env.getProperty(property, defaultValue)
    }
}

@Configuration
@PropertySource("classpath:/application.properties")
class PropertiesSourceConfig