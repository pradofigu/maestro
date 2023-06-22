package br.com.pradofigu.maestro.domain.orders

import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
import java.util.*

interface Orders {
    fun save(order: CreateOrder): Order?
    fun findAll(): List<Order>
    fun findBy(id: UUID): Order?
    fun update(id: UUID, paymentStatus: PaymentStatus): Order
    fun delete(id: UUID): Boolean
    fun findBy(number: Long): Order?
}
