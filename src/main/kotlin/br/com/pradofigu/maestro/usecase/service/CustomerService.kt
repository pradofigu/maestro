package br.com.pradofigu.maestro.usecase.service

import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.model.Customer
import br.com.pradofigu.maestro.usecase.persistence.CustomerDataAccessPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val customerDataAccessPort: CustomerDataAccessPort
) {

    suspend fun register(customer: Customer): Customer = customerDataAccessPort.save(customer)

    suspend fun findAll(): List<Customer> = customerDataAccessPort.findAll()

    suspend fun findBy(id: UUID): Customer? = customerDataAccessPort.findBy(id)

    suspend fun findBy(cpf: String): Customer? {
        val validCpf = CPF(cpf)
        return customerDataAccessPort.findBy(validCpf.number)
    }

    suspend fun update(id: UUID, customer: Customer): Customer {
        return customerDataAccessPort.update(id, customer)
    }

    suspend fun delete(id: UUID) {
        customerDataAccessPort.delete(id)
    }
}