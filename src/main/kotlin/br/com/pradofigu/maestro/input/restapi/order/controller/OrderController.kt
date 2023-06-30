package br.com.pradofigu.maestro.input.restapi.order.controller

import br.com.pradofigu.maestro.domain.order.ports.input.OrderInputPort
import br.com.pradofigu.maestro.input.restapi.order.dto.OrderRequest
import br.com.pradofigu.maestro.input.restapi.order.dto.OrderResponse
import org.springframework.beans.factory.annotation.Autowired
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
@RequestMapping(value = ["/orders"], produces = [APPLICATION_JSON_VALUE])
class OrderController(@Autowired private val orderInputPort: OrderInputPort) {

    @PostMapping
    suspend fun createOrder(@RequestBody request: OrderRequest): ResponseEntity<OrderResponse> {
        val order = orderInputPort.createOrder(request.toModel())
        return ResponseEntity(OrderResponse.from(order), CREATED)
    }

    @GetMapping
    suspend fun findAll(): ResponseEntity<List<OrderResponse>> {
        val orders = orderInputPort.findAll().map { OrderResponse.from(it) }
        return ResponseEntity.ok(orders)
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): ResponseEntity<OrderResponse> {
        return orderInputPort.findBy(UUID.fromString(id))?.let {
            ResponseEntity.ok(OrderResponse.from(it))
        }?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: String,
        @RequestBody request: OrderRequest
    ): ResponseEntity<OrderResponse> {
        return orderInputPort.updatePaymentStatus(
            UUID.fromString(id),
            request.paymentStatus
        ).let { ResponseEntity.ok(OrderResponse.from(it)) }
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        orderInputPort.delete(UUID.fromString(id))
        return ResponseEntity.ok().build()
    }

    @GetMapping("/number/{number}")
    suspend fun findByNumber(@PathVariable number: UUID): ResponseEntity<OrderResponse> {
        return orderInputPort.findBy(number)?.let {
            ResponseEntity.ok(OrderResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }
}