package br.com.pradofigu.maestro.domain.orders

import java.util.*

interface Orders {
    fun save(order: Order): Order
    fun findAll(): List<Order>
    fun findBy(id: UUID): Order
    fun update(id: UUID, paymentStatus: PaymentStatus): Order
    fun delete(id: UUID): Boolean
    fun findBy(number: Long): Order
}
