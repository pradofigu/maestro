package br.com.pradofigu.maestro.output.database.customer.adapter

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.output.database.customer.entity.CustomerEntity
import br.com.pradofigu.maestro.output.database.customer.repository.CustomerRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import java.util.*

class CustomerDataAccessAdapter(
    private val customerRepository: CustomerRepository
): CustomerDataAccessPort {
    override suspend fun update(customer: Customer) {
        customerRepository.run { save(CustomerEntity.fromModel(customer)) }
    }

    override suspend fun findAll(): List<Customer> {
        return customerRepository.findAll()
            .asFlow()
            .toList()
            .map { it.toModel() }
    }

    override suspend fun findById(id: UUID): Customer? {
        return customerRepository.findById(id).awaitSingleOrNull()?.toModel()
    }

    override suspend fun findByCpf(cpf: String): Customer? {
        return customerRepository.findByCpf(cpf).awaitSingleOrNull()?.toModel()
    }
}