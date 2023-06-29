package br.com.pradofigu.maestro.domain.orders.ports.input

import br.com.pradofigu.maestro.domain.orders.model.Order
import br.com.pradofigu.maestro.domain.orders.model.PaymentStatus
import java.util.UUID

interface OrderInputPort {
    fun createOrder(order: Order): Order

    fun findAll(): List<Order>

    fun findBy(id: UUID): Order

    fun updatePaymentStatus(id: UUID, paymentStatus: PaymentStatus): Order

    fun delete(id: UUID): Boolean
}