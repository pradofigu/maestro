package br.com.pradofigu.maestro.resources.customers

import br.com.pradofigu.maestro.domain.customers.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("/customers")
class CustomerResource(@Autowired private val service: CustomerService) {
    @GetMapping
    suspend fun findAll(): List<CustomerResponse> {
        return service.findAll().map { customer -> CustomerResponse.from(customer) }
    }

    @GetMapping("/{customerId}")
    suspend fun findById(@PathVariable customerId: String): CustomerResponse {
        val customer = service.findById(UUID.fromString(customerId))
        return CustomerResponse.from(customer)
    }

    @GetMapping("/cpf/{cpf}")
    suspend fun findByCpf(@PathVariable cpf: String): CustomerResponse {
        val customer = service.findByCpf(cpf)
        return CustomerResponse.from(customer)
    }
}