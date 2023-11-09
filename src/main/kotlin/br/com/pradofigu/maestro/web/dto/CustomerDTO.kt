package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import java.time.LocalDate
import java.util.*

data class CustomerRequest(
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate,
    val address: AddressRequest
) {
    fun toModel() = Customer(
        name = name,
        cpf = CPF(cpf),
        email = email,
        phone = phone,
        birthDate = birthDate,
        address = address.toModel()
    )
}

data class CustomerResponse(
    val id: UUID,
    val name: String?,
    val cpf: String?,
    val email: String?,
    val phone: String?,
    val birthDate: LocalDate?,
    val address: AddressResponse?
) {
    companion object {
        fun from(customer: Customer) = CustomerResponse(
            id = customer.id!!,
            name = customer.name,
            cpf = customer.cpf?.number,
            email = customer.email,
            phone = customer.phone,
            birthDate = customer.birthDate,
            address = customer.address?.let { AddressResponse.from(it) }
        )
    }
}
