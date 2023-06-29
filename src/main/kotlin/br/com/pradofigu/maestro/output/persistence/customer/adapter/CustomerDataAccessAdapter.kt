package br.com.pradofigu.maestro.output.persistence.customer.adapter

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.customer.ports.output.CustomerDataAccessPort
import br.com.pradofigu.maestro.output.persistence.customer.repository.CustomerRepository
import java.util.*

class CustomerDataAccessAdapter(
    private val customerRepository: CustomerRepository
): CustomerDataAccessPort {
    override fun save(customer: Customer.CreateCustomer): Customer? {
        return this.customerRepository.save(customer)
    }

    override fun findBy(id: UUID): Customer? {
        return this.customerRepository.findBy(id)
    }

    override fun findBy(cpf: CPF): Customer? {
        return this.customerRepository.findBy(cpf)
    }

    override fun update(id: UUID, customer: Customer.UpdateCustomer): Customer? {
        return this.customerRepository.update(id, customer)
    }

    override fun delete(id: UUID) {
        this.customerRepository.delete(id)
    }

}