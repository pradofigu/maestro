package br.com.pradofigu.maestro.persistence.repository

import br.com.pradofigu.maestro.persistence.entity.OrderTrackingEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface OrderTrackingRepository: CrudRepository<OrderTrackingEntity, UUID> {

    fun findByOrderId(orderId: UUID): OrderTrackingEntity?
}