package br.com.pradofigu.maestro.web.controller

import br.com.pradofigu.maestro.usecase.service.OrderService
import br.com.pradofigu.maestro.web.dto.*
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/orders"], produces = [APPLICATION_JSON_VALUE])
class OrderController(private val orderService: OrderService) {

    @PostMapping
    suspend fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<CreateOrderResponse> {
        val orderCreated = orderService.create(request.toModel()).let {
            CreateOrderResponse(it.id!!, it.number!!)
        }

        return ResponseEntity(orderCreated, CREATED)
    }

    @PostMapping("/{orderId}/payment")
    //TODO: (Implementar JWT) @PreAuthorize("hasRole('ROLE_USER')")
    //FIXME: This is meant to be a webhook, so it should be a POST request
    suspend fun payment(
        @PathVariable orderId: UUID,
        @RequestBody payOrderRequest: PayOrderRequest
    ): ResponseEntity<OrderResponse> {
        return orderService.processPayment(payOrderRequest.toModel(orderId))
            .let { ResponseEntity.ok(OrderResponse.from(it)) }
    }

    @GetMapping("/number/{number}")
    suspend fun findByNumber(@PathVariable number: Long): ResponseEntity<OrderResponse> {
        return orderService.findByNumber(number)?.let {
            ResponseEntity.ok(OrderResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{orderId}/tracking")
    suspend fun getTrackingDetails(@PathVariable orderId: UUID): ResponseEntity<TrackingDetailsResponse> {
        return orderService.findTrackingDetails(orderId)?.let {
            ResponseEntity.ok(TrackingDetailsResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @PutMapping("/{orderId}/tracking")
    suspend fun updateOrderTracking(
        @PathVariable orderId: UUID,
        @RequestBody request: OrderTrackingRequest
    ): ResponseEntity<Any> {
        orderService.updateOrderTracking(orderId, request.status)
        return ResponseEntity.accepted().build()
    }
}