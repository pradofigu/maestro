package br.com.pradofigu.maestro.domain.config

import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
@PropertySource("classpath:/application.properties")
class ConfigService(val env: Environment) {

    fun getProperty(property: String): String? = env.getProperty(property)

    fun getString(property: String, default: String): String = env.getProperty(property) ?: default

    fun getInt(property: String, default: Int): Int = env.getProperty(property)?.toInt() ?: default
}


