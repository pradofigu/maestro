package br.com.pradofigu.maestro.domain.customers

import br.com.pradofigu.maestro.domain.orders.Order
import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
import br.com.pradofigu.maestro.domain.orders.Order.UpdateStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class OrderService(@Autowired private val orders: Orders) {

    fun createOrder(order: CreateOrder): Order {
        return orders.save(order) ?:
        throw IllegalArgumentException("Error to create order")
    }

    fun findBy(id: UUID): Order? {
        return orders.findBy(id)
    }

    fun findBy(orderNumber: Long): Order? {
        return orders.findBy(orderNumber)
    }

    fun updateStatus(order: UpdateStatus): Order? {
        return orders.update(order) ?:
        throw IllegalArgumentException("Error to update order status")
    }

    fun delete(id: UUID): Boolean {
        return orders.delete(id)
    }

}