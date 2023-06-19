package br.com.pradofigu.maestro.resources

import br.com.pradofigu.maestro.domain.customer.CustomerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("/customers")
class CustomerController(private val service: CustomerService) {
    @GetMapping
    suspend fun findAll(): List<CustomerResponse> {
        return service.findAll().map { customer ->  CustomerResponse.from(customer)}
    }

    @GetMapping("/{customerId}")
    suspend fun findById(@PathVariable customerId: UUID): CustomerResponse {
        val customer = service.findById(customerId)
        return CustomerResponse.from(customer)
    }

    @GetMapping("/cpf/{cpf}")
    suspend fun findById(@PathVariable cpf: String): CustomerResponse {
        val customer = service.findByCpf(cpf)
        return CustomerResponse.from(customer)
    }
}