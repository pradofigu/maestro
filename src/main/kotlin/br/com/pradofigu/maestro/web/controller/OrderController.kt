package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.service.OrderService
import br.com.pradofigu.maestro.web.dto.*
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/orders"], produces = [APPLICATION_JSON_VALUE])
class OrderController(private val orderService: OrderService) {

    @PostMapping
    suspend fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<CreateOrderResponse> {
        val order = orderService.create(request.toModel())
        return ResponseEntity(CreateOrderResponse.from(order), CREATED)
    }

    @PutMapping("/{id}/payment")
    // TODO Implementar JWT
    suspend fun payment(@PathVariable id: String, @RequestBody paymentToken: PayOrderRequest): ResponseEntity<OrderResponse> {
        return orderService.process(paymentToken.toModel(id))
            .let { ResponseEntity.ok(OrderResponse.from(it)) }
    }

    @GetMapping("/number/{number}")
    suspend fun findByNumber(@PathVariable number: Long): ResponseEntity<OrderResponse> {
        return orderService.findByNumber(number)?.let {
            ResponseEntity.ok(OrderResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/tracking")
    suspend fun getTrackingDetails(): ResponseEntity<List<TrackingDetailsResponse>> {
        return orderService.findTrackingDetails().let {
            ResponseEntity.ok(it.map { orderTracking ->  TrackingDetailsResponse.from(orderTracking) })
        }
    }

    @PutMapping("/{id}/tracking")
    suspend fun updateOrderTracking(
        @PathVariable id: String,
        @RequestBody request: OrderTrackingRequest
    ): ResponseEntity<Any> {
        orderService.updateOrderTracking(id, request.status)
        return ResponseEntity.accepted().build()
    }
}