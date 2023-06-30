package br.com.pradofigu.maestro.domain.order.ports.output

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import java.util.UUID

interface OrderDataAccessPort {

    fun findAll(): List<Order>

    fun findBy(id: UUID): Order?

    fun findBy(number: Long): Order?

    fun save(order: Order): Order

    fun update(id: UUID, paymentStatus: PaymentStatus): Order

    fun delete(id: UUID)
}
