package br.com.pradofigu.maestro.domain.order.ports.output

import br.com.pradofigu.maestro.domain.order.model.*

interface OrderDataAccessPort {

    suspend fun findByNumber(number: Long): Order?

    suspend fun save(order: CreateOrder): PendingPaymentOrder

    suspend fun process(orderPayment: OrderPayment): Order

    suspend fun findTrackingDetails(): List<OrderTracking>

    suspend fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking
}
