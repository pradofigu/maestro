package br.com.pradofigu.maestro.input.http.customer.controller

import br.com.pradofigu.maestro.domain.customer.ports.input.OrderInputPort
import br.com.pradofigu.maestro.input.http.customer.dto.OrderDTO
import br.com.pradofigu.maestro.input.http.customer.dto.toDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController("/orders")
class OrderController(private val orderInputPort: OrderInputPort) {

    @GetMapping
    suspend fun findAll(): List<OrderDTO> {
        return orderInputPort.findAll().map { it.toDTO() }
    }

    @GetMapping("/{orderId}")
    suspend fun findById(@PathVariable orderId: UUID): ResponseEntity<OrderDTO> {
        val result = orderInputPort.findById(orderId)

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/orderNumber/{orderNumber}")
    suspend fun findByOrderNumber(@PathVariable orderNumber: Number): ResponseEntity<OrderDTO> {
        val result = orderInputPort.findByOrderNumber(orderNumber)

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    suspend fun create(@RequestBody order: OrderDTO): ResponseEntity<OrderDTO> {
        val result = orderInputPort.create(order.toModel())

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @PutMapping
    suspend fun updateStatus(@RequestBody order: OrderDTO): ResponseEntity<OrderDTO> {
        val result = orderInputPort.updateStatus(order.toModel())

        return result?.let {
            ResponseEntity.ok(it.toDTO())
        } ?: ResponseEntity(HttpStatus.BAD_REQUEST)
    }

}