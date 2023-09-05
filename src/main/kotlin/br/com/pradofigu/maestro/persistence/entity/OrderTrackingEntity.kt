package br.com.pradofigu.maestro.persistence.entity

import br.com.pradofigu.maestro.usecase.model.OrderStatus
import br.com.pradofigu.maestro.usecase.model.OrderTracking
import jakarta.persistence.*

@Entity
@Table(name = "order_tracking")
data class OrderTrackingEntity(
    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    val order: OrderEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: OrderStatus = OrderStatus.RECEIVED
): AbstractEntity() {

    fun toModel(): OrderTracking = OrderTracking(
        id = this.id!!,
        order = this.order!!.toModel(),
        status = this.status,
        createdAt = this.createdAt
    )
}