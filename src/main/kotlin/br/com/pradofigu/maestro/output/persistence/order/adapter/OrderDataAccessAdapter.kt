package br.com.pradofigu.maestro.output.persistence.order.adapter

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.order.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.output.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderDataAccessAdapter(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {


    override suspend fun findAll(): List<Order> = orderRepository.findAll()

    override suspend fun findBy(id: UUID): Order? = orderRepository.findBy(id)

    override suspend fun findBy(number: Long): Order? = orderRepository.findBy(number)

    override suspend fun save(order: Order): Order = orderRepository.save(order)
        ?: throw DatabaseOperationException("Error to save order", order)

    override suspend fun update(id: UUID, paymentStatus: PaymentStatus): Order {
        return orderRepository.update(id, paymentStatus)
            ?: throw DatabaseOperationException(
                "Error to update order",
                mapOf("id" to id, "paymentStatus" to paymentStatus.name)
            )
    }

    override suspend fun delete(id: UUID) {
        orderRepository.delete(id).also {
            if (!it) throw DatabaseOperationException("Error to delete order with ID $id")
        }
    }
}