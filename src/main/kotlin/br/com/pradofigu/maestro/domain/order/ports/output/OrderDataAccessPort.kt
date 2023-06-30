package br.com.pradofigu.maestro.domain.order.ports.output

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import java.util.UUID

interface OrderDataAccessPort {

    suspend fun findAll(): List<Order>

    suspend fun findBy(id: UUID): Order?

    suspend fun findBy(number: Long): Order?

    suspend fun save(order: Order): Order

    suspend fun update(id: UUID, paymentStatus: PaymentStatus): Order

    suspend fun delete(id: UUID)
}
