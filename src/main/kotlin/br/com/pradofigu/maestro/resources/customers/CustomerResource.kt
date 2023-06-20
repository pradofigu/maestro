package br.com.pradofigu.maestro.resources.customers

import br.com.pradofigu.maestro.domain.customers.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/customers"], produces = [APPLICATION_JSON_VALUE])
class CustomerResource(@Autowired private val service: CustomerService) {

    @PostMapping
    suspend fun register(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = service.register(request.toCreateCustomer())
        return ResponseEntity(CustomerResponse.from(customer), CREATED)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): CustomerResponse? {
        val maybeCustomer = service.findBy(UUID.fromString(id))
        return maybeCustomer?.let { customer -> CustomerResponse.from(customer) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: CustomerRequest): CustomerResponse {
        val customer = service.update(UUID.fromString(id), request.toUpdateCustomer())
        return CustomerResponse.from(customer)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }
}