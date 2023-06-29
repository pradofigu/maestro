package br.com.pradofigu.maestro.domain.orders.ports.output

import br.com.pradofigu.maestro.domain.orders.model.Order
import br.com.pradofigu.maestro.domain.orders.model.PaymentStatus
import java.util.*

interface OrderDataAccessPort {
    fun save(order: Order): Order
    fun findAll(): List<Order>
    fun findBy(id: UUID): Order
    fun update(id: UUID, paymentStatus: PaymentStatus): Order
    fun delete(id: UUID): Boolean
    fun findBy(number: Long): Order
}
