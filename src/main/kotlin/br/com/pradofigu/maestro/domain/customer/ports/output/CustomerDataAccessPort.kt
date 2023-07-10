package br.com.pradofigu.maestro.domain.customer.ports.output

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

interface CustomerDataAccessPort {

    suspend fun findAll(): List<Customer>

    suspend fun findBy(id: UUID): Customer?

    suspend fun findBy(cpf: CPF): Customer?

    suspend fun save(customer: Customer): Customer

    suspend fun update(id: UUID, customer: Customer): Customer

    suspend fun delete(id: UUID)
}
