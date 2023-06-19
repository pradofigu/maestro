package br.com.pradofigu.maestro.domain.customers

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Customer(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String,
    val cpf: String,
    val birthDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
