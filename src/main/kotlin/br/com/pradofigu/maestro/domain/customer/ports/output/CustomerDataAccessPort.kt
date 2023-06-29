package br.com.pradofigu.maestro.domain.customer.ports.output

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.model.Customer.CreateCustomer
import java.util.*

interface CustomerDataAccessPort {

    fun save(customer: CreateCustomer): Customer?

    fun findBy(id: UUID): Customer?

    fun update(id: UUID, customer: Customer.UpdateCustomer): Customer?

    fun delete(id: UUID)

    fun findBy(cpf: CPF): Customer?

}
