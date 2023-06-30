package br.com.pradofigu.maestro.domain.customer.ports.output

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

interface CustomerDataAccessPort {

    fun findBy(id: UUID): Customer?

    fun findBy(cpf: CPF): Customer?

    fun save(customer: Customer): Customer

    fun update(id: UUID, customer: Customer): Customer

    fun delete(id: UUID)
}
