package br.com.pradofigu.maestro.input.restapi.customer.dto

import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.time.LocalDate
import java.util.UUID

data class CustomerResponse(
    val id: String,
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate
) {
    companion object {
        fun from(customer: Customer) = CustomerResponse(
            id = customer.id!!.toString(),
            name = customer.name,
            cpf = customer.cpf.number,
            email = customer.email,
            phone = customer.phone,
            birthDate = customer.birthDate
        )
    }

}
