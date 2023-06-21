package br.com.pradofigu.maestro.customers.application.services

import br.com.pradofigu.maestro.customers.application.ports.`in`.RegisterCustomerInPort
import br.com.pradofigu.maestro.customers.application.ports.out.RegisterCustomerOutPort
import br.com.pradofigu.maestro.customers.domain.Customer
import br.com.pradofigu.maestro.customers.domain.Customer.CreateCustomer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterCustomer(@Autowired private val repository: RegisterCustomerOutPort) : RegisterCustomerInPort {

    @Transactional
    override fun register(customer: CreateCustomer): Customer {
        return repository.save(customer) ?: throw IllegalArgumentException("Error to save customer")
    }

}
