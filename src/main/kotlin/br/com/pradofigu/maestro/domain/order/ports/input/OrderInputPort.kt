package br.com.pradofigu.maestro.domain.order.ports.input

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import java.util.UUID

interface OrderInputPort {
    suspend fun createOrder(order: Order): Order

    suspend fun findAll(): List<Order>

    suspend fun findBy(id: UUID): Order?

    suspend fun findBy(number: Long): Order?

    suspend fun updatePaymentStatus(id: UUID, paymentStatus: PaymentStatus): Order

    suspend fun delete(id: UUID)
}