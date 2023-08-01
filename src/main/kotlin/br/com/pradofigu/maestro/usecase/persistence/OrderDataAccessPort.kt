package br.com.pradofigu.maestro.usecase.persistence

import br.com.pradofigu.maestro.usecase.model.*

interface OrderDataAccessPort {

    suspend fun findByNumber(number: Long): Order?

    suspend fun save(order: CreateOrder): PendingPaymentOrder

    suspend fun process(orderPayment: OrderPayment): Order

    suspend fun findTrackingDetails(): List<OrderTracking>

    suspend fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking
}
