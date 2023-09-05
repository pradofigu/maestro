package br.com.pradofigu.maestro.usecase.persistence

import br.com.pradofigu.maestro.usecase.model.Customer
import java.util.*

interface CustomerDataAccessPort {

    suspend fun findAll(): List<Customer>

    suspend fun findBy(id: UUID): Customer?

    suspend fun findBy(cpf: String): Customer?

    suspend fun save(customer: Customer): Customer

    suspend fun update(id: UUID, customer: Customer): Customer

    suspend fun delete(id: UUID)
}
