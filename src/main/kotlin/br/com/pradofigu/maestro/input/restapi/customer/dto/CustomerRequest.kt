package br.com.pradofigu.maestro.input.restapi.customer.dto

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer.CreateCustomer
import br.com.pradofigu.maestro.domain.customer.model.Customer.UpdateCustomer
import java.time.LocalDate

data class CustomerRequest(
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate
) {
    fun toCreateCustomer() : CreateCustomer {
        return CreateCustomer(
            name = this.name,
            cpf = CPF(this.cpf),
            email = this.email,
            phone = this.phone,
            birthDate = this.birthDate
        )
    }

    fun toUpdateCustomer() : UpdateCustomer {
        return UpdateCustomer(
            name = this.name,
            cpf = CPF(this.cpf),
            email = this.email,
            phone = this.phone,
            birthDate = this.birthDate
        )
    }

}