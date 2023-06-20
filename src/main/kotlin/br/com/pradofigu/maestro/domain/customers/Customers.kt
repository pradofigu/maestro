package br.com.pradofigu.maestro.domain.customers

import br.com.pradofigu.maestro.domain.customers.Customer.CreateCustomer
import java.util.*

interface Customers {

    fun save(customer: CreateCustomer): Customer?

    fun findBy(id: UUID): Customer?

    fun update(id: UUID, customer: Customer.UpdateCustomer): Customer?

    fun delete(id: UUID): Boolean

}
