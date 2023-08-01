package br.com.pradofigu.maestro.usecase.service

import br.com.pradofigu.maestro.usecase.model.*
import br.com.pradofigu.maestro.usecase.persistence.OrderDataAccessPort
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderDataAccessPort: OrderDataAccessPort) {

    suspend fun create(order: CreateOrder): PendingPaymentOrder = orderDataAccessPort.save(order)

    suspend fun findByNumber(number: Long): Order? = orderDataAccessPort.findByNumber(number)

    suspend fun process(orderPayment: OrderPayment): Order = orderDataAccessPort.process(orderPayment)

    suspend fun findTrackingDetails(): List<OrderTracking> =
        orderDataAccessPort.findTrackingDetails()

    suspend fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking =
        orderDataAccessPort.updateOrderTracking(id, orderStatus)
}