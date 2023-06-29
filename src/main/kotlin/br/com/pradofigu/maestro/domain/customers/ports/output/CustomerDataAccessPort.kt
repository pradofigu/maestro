package br.com.pradofigu.maestro.domain.customers.ports.output

import br.com.pradofigu.maestro.domain.customers.model.CPF
import br.com.pradofigu.maestro.domain.customers.model.Customer
import br.com.pradofigu.maestro.domain.customers.model.Customer.CreateCustomer
import java.util.*

interface CustomerDataAccessPort {

    fun save(customer: CreateCustomer): Customer?

    fun findBy(id: UUID): Customer?

    fun update(id: UUID, customer: Customer.UpdateCustomer): Customer?

    fun delete(id: UUID)

    fun findBy(cpf: CPF): Customer?

}
