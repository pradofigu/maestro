package br.com.pradofigu.maestro.domain.customer.model

import java.time.LocalDate
import java.util.UUID

data class Customer(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val email: String,
    val phone: String,
    val cpf: CPF,
    val birthDate: LocalDate
)
