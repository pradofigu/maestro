package br.com.pradofigu.maestro.domain.customer.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Customer(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String,
    val cpf: String,
    val birthDate: LocalDate,
    val address: Address,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class Address(
    val street: String,
    val number: String,
    val complement: String,
    val neighborhood: String,
    val city: String,
    val state: String,
    val country: String,
    val zipCode: String
)
