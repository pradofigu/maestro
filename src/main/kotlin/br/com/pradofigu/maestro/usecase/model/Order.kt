package br.com.pradofigu.maestro.usecase.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

enum class OrderStatus {
    RECEIVED,
    IN_PREPARATION,
    READY,
    FINISHED
}

data class Order(
    val id: UUID?,
    val number: Long,
    val customerId: UUID?,
    val paymentStatus: PaymentStatus
)

data class CreateOrder(
    val customerId: UUID?,
    val productsId: List<UUID>
) {
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING
}

data class OrderPayment(
    val id: UUID,
    val number: Long,
    val status: PaymentStatus
)

data class OrderTracking(
    val id: UUID,
    val orderId: UUID,
    val status: OrderStatus,
    val createdAt: LocalDateTime
) {
    var orderNumber: Long? = null
    var products: List<ProductPreparation>? = null

    fun calculatePreparationTime(): BigDecimal {
        var amount = BigDecimal.ZERO
        products!!.forEach { amount += it.preparationTime }

        return amount
    }
}