package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.persistence.repository.CustomerRepository
import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CustomerAdapterImpl(
    private val customerRepository: CustomerRepository
): CustomerDataAccessPort {

    override suspend fun findAll(): List<Customer> = customerRepository.findAll()

    override suspend fun findBy(id: UUID): Customer? = customerRepository.findBy(id)

    override suspend fun findBy(cpf: CPF): Customer? = customerRepository.findBy(cpf)

    override suspend fun save(customer: Customer): Customer = customerRepository.save(customer)

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