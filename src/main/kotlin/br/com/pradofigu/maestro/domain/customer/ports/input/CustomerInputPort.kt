package br.com.pradofigu.maestro.domain.customer.ports.input

import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

interface CustomerInputPort {

    suspend fun findAll(): List<Customer>

    suspend fun findById(id: UUID): Customer?

    suspend fun findByCpf(cpf: String): Customer?
}