package br.com.pradofigu.maestro.output.persistence.order.adapter

import br.com.pradofigu.maestro.domain.order.model.*
import br.com.pradofigu.maestro.domain.order.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.output.persistence.order.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderDataAccessAdapter(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {

    override suspend fun findByNumber(number: Long): Order? = orderRepository.findByNumber(number)

    override suspend fun save(order: CreateOrder): PendingPaymentOrder = orderRepository.save(order)

    override suspend fun process(orderPayment: OrderPayment): Order {
        return orderRepository.update(orderPayment)

    }

    override suspend fun findTracking(orderId: String): OrderTracking? {
        return orderRepository.findTracking(orderId)
    }

    override suspend fun findPreparationDetails(orderId: String): OrderTracking {
        return orderRepository.findTrackingDetails(orderId)
    }

    override suspend fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking {
        return orderRepository.updateOrderTracking(id, orderStatus)
    }
}