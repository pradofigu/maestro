package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.usecase.persistence.OrderDataAccessPort
import br.com.pradofigu.maestro.persistence.repository.OrderRepository
import br.com.pradofigu.maestro.usecase.model.Order
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class OrderAdapterImpl(
    private val orderRepository: OrderRepository
): OrderDataAccessPort {

    override suspend fun save(order: Order): Order = runBlocking {
        orderRepository.save(order.toEntity())
    }.toModel()

    override suspend fun findByNumber(number: Long): Order? = runBlocking {
        orderRepository.findByNumber(number)
    }?.toModel()
}