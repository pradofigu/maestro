package br.com.pradofigu.maestro.input.restapi.customer.controller

import br.com.pradofigu.maestro.domain.customer.model.CPF
import br.com.pradofigu.maestro.domain.customer.ports.input.CustomerInputPort
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerRequest
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/customers"], produces = [APPLICATION_JSON_VALUE])
class CustomerController(private val customerInputPort: CustomerInputPort) {

    @PostMapping
    suspend fun register(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = customerInputPort.register(request.toModel())
        return ResponseEntity(CustomerResponse.from(customer), CREATED)
    }

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<CustomerResponse>> {
        val customers = customerInputPort.findAll().map { CustomerResponse.from(it) }
        return ResponseEntity.ok(customers)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<CustomerResponse> {
        return customerInputPort.findBy(UUID.fromString(id))?.let {
            ResponseEntity.ok(CustomerResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: String,
        @RequestBody request: CustomerRequest
    ): ResponseEntity<CustomerResponse> {
        val customer = customerInputPort.update(UUID.fromString(id), request.toModel())
        return ResponseEntity.ok(CustomerResponse.from(customer))
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        customerInputPort.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/cpf/{cpf}")
    suspend fun findByCPF(@PathVariable cpf: String): ResponseEntity<CustomerResponse> {
        return customerInputPort.findBy(CPF(cpf))?.let {
            ResponseEntity.ok(CustomerResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }
}