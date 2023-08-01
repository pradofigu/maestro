package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.model.CPF
import br.com.pradofigu.maestro.usecase.service.CustomerService
import br.com.pradofigu.maestro.web.dto.CustomerRequest
import br.com.pradofigu.maestro.web.dto.CustomerResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/customers"], produces = [APPLICATION_JSON_VALUE])
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    suspend fun register(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = customerService.register(request.toModel())
        return ResponseEntity(CustomerResponse.from(customer), CREATED)
    }

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<CustomerResponse>> {
        val customers = customerService.findAll().map { CustomerResponse.from(it) }
        return ResponseEntity.ok(customers)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<CustomerResponse> {
        return customerService.findBy(UUID.fromString(id))?.let {
            ResponseEntity.ok(CustomerResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: String,
        @RequestBody request: CustomerRequest
    ): ResponseEntity<CustomerResponse> {
        val customer = customerService.update(UUID.fromString(id), request.toModel())
        return ResponseEntity.ok(CustomerResponse.from(customer))
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        customerService.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/cpf/{cpf}")
    suspend fun findByCPF(@PathVariable cpf: String): ResponseEntity<CustomerResponse> {
        return customerService.findBy(CPF(cpf))?.let {
            ResponseEntity.ok(CustomerResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }
}