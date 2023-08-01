package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.domain.order.model.*
import br.com.pradofigu.maestro.domain.order.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.persistence.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderAdapterImpl(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {

    override suspend fun findByNumber(number: Long): Order? = orderRepository.findByNumber(number)

    override suspend fun save(order: CreateOrder): PendingPaymentOrder = orderRepository.save(order)

    override suspend fun process(orderPayment: OrderPayment): Order {
        return orderRepository.update(orderPayment)

    }

    override suspend fun findTrackingDetails(): List<OrderTracking> {
        return orderRepository.findTrackingDetails()
    }

    override suspend fun updateOrderTracking(id: String, orderStatus: OrderStatus): OrderTracking {
        return orderRepository.updateOrderTracking(id, orderStatus)
    }
}