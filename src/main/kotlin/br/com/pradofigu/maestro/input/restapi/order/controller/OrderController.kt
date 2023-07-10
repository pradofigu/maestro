package br.com.pradofigu.maestro.input.restapi.order.controller

import br.com.pradofigu.maestro.domain.order.ports.input.OrderInputPort
import br.com.pradofigu.maestro.input.restapi.order.dto.*
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/orders"], produces = [APPLICATION_JSON_VALUE])
class OrderController(private val orderInputPort: OrderInputPort) {

    @PostMapping
    suspend fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<CreateOrderResponse> {
        val order = orderInputPort.create(request.toModel())
        return ResponseEntity(CreateOrderResponse.from(order), CREATED)
    }

    @PutMapping("/{id}/payment")
    // TODO Implementar JWT
    suspend fun payment(@PathVariable id: String, @RequestBody paymentToken: PayOrderRequest): ResponseEntity<OrderResponse> {
        return orderInputPort.process(paymentToken.toModel(id))
            .let { ResponseEntity.ok(OrderResponse.from(it)) }
    }

    @GetMapping("/number/{number}")
    suspend fun findByNumber(@PathVariable number: Long): ResponseEntity<OrderResponse> {
        return orderInputPort.findByNumber(number)?.let {
            ResponseEntity.ok(OrderResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/tracking")
    suspend fun findTracking(@PathVariable id: String): ResponseEntity<TrackingResponse> {
        return orderInputPort.findTracking(id)?.let {
            ResponseEntity.ok(TrackingResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/preparation")
    suspend fun getPreparationDetails(@PathVariable id: String): ResponseEntity<PreparationResponse> {
        return orderInputPort.findPreparationDetails(id).let {
            ResponseEntity.ok(PreparationResponse.from(it))
        }
    }

    @PutMapping("/{id}/tracking")
    suspend fun updateOrderTracking(@PathVariable id: String, @RequestBody request: OrderTrackingRequest): ResponseEntity<TrackingResponse> {
        return orderInputPort.updateOrderTracking(id, request.status).let {
            ResponseEntity.ok(TrackingResponse.from(it))
        }
    }
}