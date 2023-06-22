package br.com.pradofigu.maestro.domain.orders

import br.com.pradofigu.maestro.domain.orders.Order.CreateOrder
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

    fun findAll(): List<Order> {
        return orders.findAll();
    }

    fun findBy(id: UUID): Order? {
        return orders.findBy(id)
    }

    fun updateStatus(id: UUID, status: OrderStatus): Order {
        return orders.update(id, status) ?:
        throw IllegalArgumentException("Error to update order status")
    }

    fun delete(id: UUID): Boolean {
        return orders.delete(id)
    }

}