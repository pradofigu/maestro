package br.com.pradofigu.maestro.output.database.customer.adapter

import br.com.pradofigu.maestro.domain.customer.model.Order
import br.com.pradofigu.maestro.domain.customer.ports.output.OrderDataAccessPort
import br.com.pradofigu.maestro.output.database.customer.entity.OrderEntity
import br.com.pradofigu.maestro.output.database.customer.repository.OrderRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OrderDataAccessAdapter(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {
    override suspend fun create(order: Order): Order? {
        return orderRepository.save(OrderEntity.fromModel(order)).awaitSingleOrNull()?.toModel()
    }

    override suspend fun findAll(): List<Order> {
        return orderRepository.findAll()
            .asFlow()
            .toList()
            .map { it.toModel() }
    }

    override suspend fun findById(id: UUID): Order? {
        return orderRepository.findById(id).awaitSingleOrNull()?.toModel()
    }

    override suspend fun findByOrderNumber(orderNumber: Number): Order? {
        return orderRepository.findByOrderNumber(orderNumber).awaitSingleOrNull()?.toModel()
    }

    override suspend fun updateStatus(order: Order) {
        orderRepository.run { save(OrderEntity.fromModel(order)) }
    }
}