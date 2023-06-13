package br.com.pradofigu.maestro.domain.customer.ports.output

import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

interface CustomerDataAccessPort {

    suspend fun update(customer: Customer)

    suspend fun findAll(): List<Customer>

    suspend fun findById(id: UUID): Customer?

    suspend fun findByCpf(cpf: String): Customer?
}