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

    override fun register(customer: Customer): Customer = customerDataAccessPort.save(customer)

    override fun findBy(id: UUID): Customer? = customerDataAccessPort.findBy(id)

    override fun findBy(cpf: CPF): Customer? = customerDataAccessPort.findBy(cpf)

    override fun update(id: UUID, customer: Customer): Customer {
        return customerDataAccessPort.update(id, customer)
    }

    override fun delete(id: UUID) {
        customerDataAccessPort.delete(id)
    }
}