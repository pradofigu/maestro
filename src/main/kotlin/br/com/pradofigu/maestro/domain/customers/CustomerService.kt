package br.com.pradofigu.maestro.domain.customers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(@Autowired private val customers: Customers) {

    fun findAll(): List<Customer> {
        TODO("Not yet implemented")
    }

    fun findById(customerId: UUID): Customer {
        TODO("Not yet implemented")
    }

    fun findByCpf(cpf: String): Customer {
        TODO("Not yet implemented")
    }

}