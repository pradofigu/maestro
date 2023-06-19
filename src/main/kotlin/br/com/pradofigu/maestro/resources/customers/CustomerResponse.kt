package br.com.pradofigu.maestro.resources.customers

import br.com.pradofigu.maestro.domain.customers.Customer
import java.time.LocalDate
import java.time.LocalDateTime

data class CustomerResponse(
    val id: String,
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(customer: Customer) : CustomerResponse {
            return CustomerResponse(
                id= customer.id.toString(),
                name = customer.name,
                cpf = customer.cpf,
                email = customer.email,
                phone = customer.phone,
                birthDate = customer.birthDate,
                createdAt = customer.createdAt,
                updatedAt = customer.updatedAt
            )
        }
    }

}
