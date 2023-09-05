package br.com.pradofigu.maestro.persistence.adapter

import br.com.pradofigu.maestro.persistence.exception.DatabaseOperationException
import br.com.pradofigu.maestro.persistence.repository.OrderTrackingRepository
import br.com.pradofigu.maestro.usecase.model.OrderStatus
import br.com.pradofigu.maestro.usecase.model.OrderTracking
import br.com.pradofigu.maestro.usecase.persistence.OrderTrackingDataAccessPort
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OrderTrackingAdapterImpl(
    private val orderTrackingRepository: OrderTrackingRepository
): OrderTrackingDataAccessPort {

    override suspend fun save(orderTracking: OrderTracking): OrderTracking = runBlocking {
        orderTrackingRepository.save(orderTracking.toEntity())
    }.toModel()

    override suspend fun findAll(): List<OrderTracking> = runBlocking {
        orderTrackingRepository.findAllByStatusIsNot()
    }.map { it.toModel() }

    override suspend fun findByOrderId(orderId: UUID): OrderTracking? = runBlocking {
        orderTrackingRepository.findByOrderId(orderId)?.toModel()
    }

    @Throws(DatabaseOperationException::class)
    override suspend fun updateOrderStatus(orderId: UUID, orderStatus: OrderStatus): OrderTracking = runBlocking {
        val orderTracking = orderTrackingRepository.findByOrderId(orderId)
            ?: throw DatabaseOperationException("Order not found for given id $orderId while updating status")

        orderTrackingRepository.save(orderTracking.copy(status = orderStatus)).toModel()
    }
}