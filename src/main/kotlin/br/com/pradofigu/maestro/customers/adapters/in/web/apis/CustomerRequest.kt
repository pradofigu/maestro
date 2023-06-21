package br.com.pradofigu.maestro.customers.adapters.`in`.web.apis

import br.com.pradofigu.maestro.customers.domain.CPF
import br.com.pradofigu.maestro.customers.domain.Customer
import java.time.LocalDate

data class CustomerRequest(
    val name: String,
    val cpf: String,
    val email: String,
    val phone: String,
    val birthDate: LocalDate
) {
    fun toCreateCustomer() : Customer.CreateCustomer {
        return Customer.CreateCustomer(
            name = this.name,
            cpf = CPF(this.cpf),
            email = this.email,
            phone = this.phone,
            birthDate = this.birthDate
        )
    }

    fun toUpdateCustomer() : Customer.UpdateCustomer {
        return Customer.UpdateCustomer(
            name = this.name,
            cpf = CPF(this.cpf),
            email = this.email,
            phone = this.phone,
            birthDate = this.birthDate
        )
    }

}