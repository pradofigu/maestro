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

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): OrderResponse? {
        val maybeOrder = service.findBy(UUID.fromString(id))
        return maybeOrder?.let { order -> OrderResponse.from(order) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: OrderRequest): OrderResponse {
        val order = service.updateStatus(UUID.fromString(id), request.toUpdateStatus())
        return OrderResponse.from(order)
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/orderNumber/{orderNumber}")
    suspend fun findByOrderNumber(@PathVariable orderNumber: Long): OrderResponse? {
        val maybeOrder = service.findBy(orderNumber)
        return maybeOrder?.let { order -> OrderResponse.from(order) }
    }
}