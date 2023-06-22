package br.com.pradofigu.maestro.resources.orders

import br.com.pradofigu.maestro.domain.orders.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/orders"], produces = [APPLICATION_JSON_VALUE])
class OrderResource(@Autowired private val service: OrderService) {

    @PostMapping
    suspend fun createOrder(@RequestBody request: OrderRequest): ResponseEntity<OrderResponse> {
        val order = service.createOrder(request.toCreateOrder())
        return ResponseEntity(OrderResponse.from(order), CREATED)
    }

    @GetMapping
    suspend fun findAll(): List<OrderResponse> {
        val orders = service.findAll()
        return orders.map { order -> OrderResponse.from(order) }
    }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): OrderResponse? {
        val maybeOrder = service.findBy(UUID.fromString(id))
        return maybeOrder?.let { order -> OrderResponse.from(order) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: OrderRequest): OrderResponse {
        val order = service.updateStatus(UUID.fromString(id), request.toUpdateOrder().status)
        return OrderResponse.from(order)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/number/{number}")
    suspend fun findByNumber(@PathVariable number: UUID): OrderResponse? {
        val maybeOrder = service.findBy(number)
        return maybeOrder?.let { order -> OrderResponse.from(order) }
    }
}