package br.com.pradofigu.maestro.domain.customers

import br.com.pradofigu.maestro.domain.customers.Customer.CreateCustomer
import br.com.pradofigu.maestro.domain.customers.Customer.UpdateCustomer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class CustomerService(@Autowired private val customers: Customers) {

    fun register(customer: CreateCustomer): Customer {
        return customers.save(customer) ?: throw IllegalArgumentException("Error to save user")
    }

    fun findBy(id: UUID): Customer? {
        return customers.findBy(id)
    }

    fun update(id: UUID, customer: UpdateCustomer): Customer {
        return customers.update(id, customer) ?: throw IllegalArgumentException("Error to update user")
    }

    fun delete(id: UUID): Boolean {
        return customers.delete(id)
    }

}