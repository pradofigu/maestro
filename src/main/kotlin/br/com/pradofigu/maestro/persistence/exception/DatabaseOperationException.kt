package br.com.pradofigu.maestro.persistence.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class DatabaseOperationException(message: String, data: Any? = null): Exception(message) {
    private val dataJson: String? = data?.let {
        val mapper = jacksonObjectMapper()
        mapper.writeValueAsString(it)
    }

    override fun toString() = if (dataJson != null) {
        "${super.toString()}. Data: $dataJson"
    } else {
        super.toString()
    }
}