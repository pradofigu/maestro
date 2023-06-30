package br.com.pradofigu.maestro.domain.customer.ports.input

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

interface CustomerInputPort {
    suspend fun register(customer: Customer): Customer

    suspend fun findBy(id: UUID): Customer?

    suspend fun findBy(cpf: CPF): Customer?

    suspend fun update(id: UUID, customer: Customer): Customer

    suspend fun delete(id: UUID)
}