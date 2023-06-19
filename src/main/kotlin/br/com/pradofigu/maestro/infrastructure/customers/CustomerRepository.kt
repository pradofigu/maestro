package br.com.pradofigu.maestro.infrastructure.customers

import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.customers.Customers
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomerRepository(@Autowired private val context: DSLContext) : Customers {

    override fun findAll(): List<Customer> {
        TODO("Not yet implemented")
    }

    override fun findById(customerId: UUID): Customer {
        TODO("Not yet implemented")
    }

    override fun findByCpf(cpf: String): Customer {
        TODO("Not yet implemented")
    }
}