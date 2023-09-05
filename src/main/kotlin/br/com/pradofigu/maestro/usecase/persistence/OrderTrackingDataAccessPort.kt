package br.com.pradofigu.maestro.usecase.persistence

import br.com.pradofigu.maestro.usecase.model.OrderStatus
import br.com.pradofigu.maestro.usecase.model.OrderTracking
import java.util.UUID

interface OrderTrackingDataAccessPort {

    suspend fun save(orderTracking: OrderTracking): OrderTracking

    suspend fun findAll(): List<OrderTracking>

    suspend fun findByOrderId(orderId: UUID): OrderTracking?

    suspend fun updateOrderStatus(orderId: UUID, orderStatus: OrderStatus): OrderTracking
}
