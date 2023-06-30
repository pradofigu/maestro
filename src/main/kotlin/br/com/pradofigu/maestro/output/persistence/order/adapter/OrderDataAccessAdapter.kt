package br.com.pradofigu.maestro.output.persistence.order.adapter

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.order.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import java.util.UUID

class OrderDataAccessAdapter(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {


    override fun findAll(): List<Order> = orderRepository.findAll()

    override fun findBy(id: UUID): Order? = orderRepository.findBy(id)

    override fun findBy(number: Long): Order? = orderRepository.findBy(number)

    override fun save(order: Order): Order = orderRepository.save(order)
        ?: throw DatabaseOperationException("Error to save order", order)

    override fun update(id: UUID, paymentStatus: PaymentStatus): Order {
        return orderRepository.update(id, paymentStatus)
            ?: throw DatabaseOperationException(
                "Error to update order",
                mapOf("id" to id, "paymentStatus" to paymentStatus.name)
            )
    }

    override fun delete(id: UUID) {
        orderRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete order with ID $id")
        }
    }
}