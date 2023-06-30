package br.com.pradofigu.maestro.domain.customer.usecase

import br.com.pradofigu.maestro.domain.customer.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.ports.input.CustomerInputPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CustomerUseCase(
    private val customerDataAccessPort: CustomerDataAccessPort
): CustomerInputPort {

    override suspend fun register(customer: Customer): Customer = customerDataAccessPort.save(customer)

    override suspend fun findBy(id: UUID): Customer? = customerDataAccessPort.findBy(id)

    override suspend fun findBy(cpf: CPF): Customer? = customerDataAccessPort.findBy(cpf)

    override suspend fun update(id: UUID, customer: Customer): Customer {
        return customerDataAccessPort.update(id, customer)
    }

    override suspend fun delete(id: UUID) {
        customerDataAccessPort.delete(id)
    }
}