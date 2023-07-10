package br.com.pradofigu.maestro.domain.order.ports.output

import br.com.pradofigu.maestro.domain.order.model.*

interface OrderDataAccessPort {

    suspend fun findByNumber(number: Long): Order?

    suspend fun save(order: CreateOrder): PendingPaymentOrder

    suspend fun process(orderPayment: OrderPayment): Order

    suspend fun findTracking(orderId: String): OrderTracking?

    suspend fun findPreparationDetails(orderId: String): OrderTracking

    suspend fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking
}
