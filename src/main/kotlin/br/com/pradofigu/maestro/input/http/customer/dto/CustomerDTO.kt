package br.com.pradofigu.maestro.input.http.customer.dto

import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class CustomerDTO(
    val id: UUID,
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

fun Customer.toDTO() = CustomerDTO(
    id = id,
    name = name,
    cpf = cpf,
    email = email,
    phone = phone,
    birthDate = birthDate,
    createdAt = createdAt,
    updatedAt = updatedAt
)
