package br.com.pradofigu.maestro.input.http.customer.controller

import br.com.pradofigu.maestro.domain.customer.ports.input.CustomerInputPort
import br.com.pradofigu.maestro.input.http.customer.dto.CustomerDTO
import br.com.pradofigu.maestro.input.http.customer.dto.toDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("/customers")
class CustomerController(private val customerInputPort: CustomerInputPort) {
    @GetMapping
    suspend fun findAll(): List<CustomerDTO> {
        return customerInputPort.findAll().map { it.toDTO() }
    }

    @GetMapping("/{customerId}")
    suspend fun findById(@PathVariable customerId: UUID): ResponseEntity<CustomerDTO> {
        val result = customerInputPort.findById(customerId)

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/cpf/{cpf}")
    suspend fun findById(@PathVariable cpf: String): ResponseEntity<CustomerDTO> {
        val result = customerInputPort.findByCpf(cpf)

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}