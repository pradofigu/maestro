package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.time.LocalDate

data class CustomerRequest(
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate
) {
    fun toModel() = Customer(
        name = name,
        cpf = CPF(cpf),
        email = email,
        phone = phone,
        birthDate = birthDate
    )
}