package br.com.pradofigu.maestro.domain.orders

import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(private val orders: Orders) {

    fun createOrder(order: Order): Order = orders.save(order)

    fun findAll(): List<Order> = orders.findAll()

    fun findBy(id: UUID): Order = orders.findBy(id)

    fun updatePaymentStatus(id: UUID, paymentStatus: PaymentStatus)
        : Order = orders.update(id, paymentStatus)

    fun delete(id: UUID): Boolean = orders.delete(id)
}