package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.persistence.entity.OrderTrackingEntity
import br.com.pradofigu.maestro.usecase.model.OrderStatus
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface OrderTrackingRepository: CrudRepository<OrderTrackingEntity, UUID> {

    fun findByOrderId(orderId: UUID): OrderTrackingEntity?

    fun findAllByStatusIsNot(status: OrderStatus = OrderStatus.FINISHED): List<OrderTrackingEntity>
}