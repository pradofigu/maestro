package br.com.pradofigu.maestro.input.restapi.customer.dto

import br.com.pradofigu.maestro.domain.customers.model.Customer
import java.time.LocalDate

data class CustomerResponse(
    val id: String,
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate
) {
    companion object {
        fun from(customer: Customer) : CustomerResponse {
            return CustomerResponse(
                id= customer.id.toString(),
                name = customer.name,
                cpf = customer.cpf.number,
                email = customer.email,
                phone = customer.phone,
                birthDate = customer.birthDate
            )
        }
    }

}
