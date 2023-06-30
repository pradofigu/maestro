package br.com.pradofigu.maestro.domain.customer.ports.input

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

interface CustomerInputPort {
    fun register(customer: Customer.CreateCustomer): Customer

    fun findBy(id: UUID): Customer?

    fun findBy(cpf: CPF): Customer?

    fun update(id: UUID, customer: Customer.UpdateCustomer): Customer

    fun delete(id: UUID): Boolean
}