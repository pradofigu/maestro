package br.com.pradofigu.maestro.output.persistence.customer.adapter

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.output.persistence.customer.repository.CustomerRepository
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import java.util.UUID

class CustomerDataAccessAdapter(
    private val customerRepository: CustomerRepository
): CustomerDataAccessPort {
    override suspend fun findBy(id: UUID): Customer? = customerRepository.findBy(id)

    override suspend fun findBy(cpf: CPF): Customer? = customerRepository.findBy(cpf)

    override suspend fun save(customer: Customer): Customer = customerRepository.save(customer)
        ?: throw DatabaseOperationException("Error to save customer", customer)

    override suspend fun update(id: UUID, customer: Customer): Customer {
        return customerRepository.update(id, customer)
            ?: throw DatabaseOperationException(
                "Error to update customer",
                mapOf("id" to id, "customer" to customer)
            )
    }

    override suspend fun delete(id: UUID) {
        customerRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete customer with ID $id")
        }
    }
}