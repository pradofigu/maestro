package br.com.pradofigu.maestro.usecase.model

import br.com.pradofigu.maestro.persistence.entity.OrderEntity
import br.com.pradofigu.maestro.persistence.entity.OrderTrackingEntity
import java.time.LocalDateTime
import java.util.UUID

enum class OrderStatus {
    RECEIVED,
    IN_PREPARATION,
    READY,
    FINISHED
}

data class Order(
    val id: UUID? = null,
    val number: Long? = null,
    val customer: Customer? = null,
    val products: List<Product> = emptyList(),
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING
) {
    fun toEntity(): OrderEntity = OrderEntity(
        number = this.number ?: 0,
        customer = this.customer?.toEntity(),
        products = this.products.map { it.toEntity() }.toSet(),
        paymentStatus = this.paymentStatus,
    ).apply { this.id = this@Order.id }
}

data class CreateOrder(
    val customerId: UUID?,
    val productsId: List<UUID>
)

data class OrderTracking(
    val id: UUID? = null,
    val order: Order,
    val status: OrderStatus = OrderStatus.RECEIVED,
    val products: List<Product> = emptyList(),
    val createdAt: LocalDateTime? = null,
) {

    fun toEntity(): OrderTrackingEntity = OrderTrackingEntity(
        order = this.order.toEntity(),
        status = this.status
    ).apply { this.id = this@OrderTracking.id }
}