package br.com.pradofigu.maestro.domain.customers

import java.time.LocalDate
import java.util.*

data class Customer(
    val id: UUID,
    val name: String,
    val email: String,
    val phone: String,
    val cpf: String,
    val birthDate: LocalDate) {

    class CreateCustomer(
        val name: String,
        val email: String,
        val phone: String,
        val cpf: String,
        val birthDate: LocalDate,
    )

    class UpdateCustomer(
        val name: String,
        val email: String,
        val phone: String,
        val cpf: String,
        val birthDate: LocalDate,
    )
}
