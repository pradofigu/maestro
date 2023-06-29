package br.com.pradofigu.maestro.input.restapi.customer.controller

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.ports.input.CustomerInputPort
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerRequest
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/customers"], produces = [APPLICATION_JSON_VALUE])
class CustomerController(@Autowired private val customerInputPort: CustomerInputPort) {

    @PostMapping
    suspend fun register(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = customerInputPort.register(request.toCreateCustomer())
        return ResponseEntity(CustomerResponse.from(customer), CREATED)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): CustomerResponse? {
        val maybeCustomer = customerInputPort.findBy(UUID.fromString(id))
        return maybeCustomer?.let { customer -> CustomerResponse.from(customer) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: CustomerRequest): CustomerResponse {
        val customer = customerInputPort.update(UUID.fromString(id), request.toUpdateCustomer())
        return CustomerResponse.from(customer)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        customerInputPort.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/cpf/{cpf}")
    suspend fun findByCPF(@PathVariable cpf: String): CustomerResponse? {
        val maybeCustomer = customerInputPort.findBy(CPF(cpf))
        return maybeCustomer?.let { customer -> CustomerResponse.from(customer) }
    }
}