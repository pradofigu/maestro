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
        return service.createOrder(request.toCreateOrder()).let {
            ResponseEntity(OrderResponse.from(it), CREATED)
        }
    }

    @GetMapping
    suspend fun findAll(): List<OrderResponse> = service.findAll().map { OrderResponse.from(it) }

    @GetMapping("/{id}")
    suspend fun findById(@PathVariable id: String): OrderResponse? {
        val maybeOrder = service.findBy(UUID.fromString(id))
        return maybeOrder?.let { order -> OrderResponse.from(order) }
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: String, @RequestBody request: OrderRequest): OrderResponse {
        return service.updatePaymentStatus(UUID.fromString(id), request.toUpdatePaymentStatus().paymentStatus).let {
            OrderResponse.from(it)
        }
    }

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: String): ResponseEntity<Any> {
        service.delete(UUID.fromString(id))
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/number/{number}")
    suspend fun findByNumber(@PathVariable number: UUID): OrderResponse? = service.findBy(number)?.let {
        OrderResponse.from(it)
    }
}