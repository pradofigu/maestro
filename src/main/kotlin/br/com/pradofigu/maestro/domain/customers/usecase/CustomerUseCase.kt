package br.com.pradofigu.maestro.domain.customers.usecase

import br.com.pradofigu.maestro.domain.customers.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.domain.customers.model.CPF
import br.com.pradofigu.maestro.domain.customers.model.Customer
import br.com.pradofigu.maestro.domain.customers.model.Customer.CreateCustomer
import br.com.pradofigu.maestro.domain.customers.model.Customer.UpdateCustomer
import br.com.pradofigu.maestro.domain.customers.ports.input.CustomerInputPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class CustomerUseCase(@Autowired private val customerDataAccessPort: CustomerDataAccessPort): CustomerInputPort {

    override fun register(customer: CreateCustomer): Customer {
        return customerDataAccessPort.save(customer) ?:
         throw IllegalArgumentException("Error to save user")
    }

    override fun findBy(id: UUID): Customer? {
        return customerDataAccessPort.findBy(id)
    }

    override fun findBy(cpf: CPF): Customer? {
        return customerDataAccessPort.findBy(cpf)
    }

    override fun update(id: UUID, customer: UpdateCustomer): Customer {
        return customerDataAccessPort.update(id, customer) ?:
        throw IllegalArgumentException("Error to update user")
    }

    override fun delete(id: UUID): Boolean {
        return customerDataAccessPort.delete(id)
    }
}