package br.com.pradofigu.maestro.output.persistence.order.adapter

import br.com.pradofigu.maestro.domain.orders.model.Order
import br.com.pradofigu.maestro.domain.orders.model.PaymentStatus
import br.com.pradofigu.maestro.domain.orders.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import java.util.UUID

class OrderDataAccessAdapter(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {

    override fun save(order: Order): Order {
        return orderRepository.save(order)
    }

    override fun findAll(): List<Order> {
        return orderRepository.findAll()
    }

    override fun findBy(id: UUID): Order {
        return orderRepository.findBy(id)
    }

    override fun findBy(number: Long): Order {
        return orderRepository.findBy(number)
    }

    override fun update(id: UUID, paymentStatus: PaymentStatus): Order {
        return orderRepository.update(id, paymentStatus)
    }

    override fun delete(id: UUID): Boolean {
        return orderRepository.delete(id)
    }
}