package br.com.pradofigu.maestro.domain.customer.ports.output

import br.com.pradofigu.maestro.domain.customer.model.Order
import java.util.*

interface OrderDataAccessPort {
    suspend fun findAll(): List<Order>
    suspend fun findById(id: UUID): Order?
    suspend fun findByOrderNumber(orderNumber: Number): Order?
    suspend fun create(order: Order): Order?
    suspend fun updateStatus(order: Order): Order?
}