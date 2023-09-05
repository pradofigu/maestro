package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.persistence.repository.CustomerRepository
import br.com.pradofigu.maestro.usecase.model.Customer
import br.com.pradofigu.maestro.usecase.persistence.CustomerDataAccessPort
import kotlinx.coroutines.runBlocking
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull

@Service
class CustomerAdapterImpl(
    private val customerRepository: CustomerRepository
): CustomerDataAccessPort {

    override suspend fun findAll(): List<Customer> = runBlocking {
        customerRepository.findAll().map { it.toModel() }
    }

    override suspend fun findBy(id: UUID): Customer? = runBlocking {
        customerRepository.findByIdOrNull(id)?.toModel()
    }

    override suspend fun findBy(cpf: String): Customer? = runBlocking {
        customerRepository.findByCpf(cpf).getOrNull()?.toModel()
    }

    override suspend fun save(customer: Customer): Customer = runBlocking {
        customerRepository.save(customer.toEntity()).toModel()
    }

    @Throws(DatabaseOperationException::class)
    override suspend fun update(id: UUID, customer: Customer): Customer = runBlocking {
        val updatableCustomer = customerRepository.findById(id).getOrElse {
            throw DatabaseOperationException("Customer with ID $id not found while updating")
        }.copy(
            name = customer.name,
            email = customer.email,
            phone = customer.phone,
            cpf = customer.cpf?.number,
            birthDate = customer.birthDate
        )

        customerRepository.save(updatableCustomer).toModel()
    }

    override suspend fun delete(id: UUID) = runBlocking { customerRepository.deleteById(id) }
}