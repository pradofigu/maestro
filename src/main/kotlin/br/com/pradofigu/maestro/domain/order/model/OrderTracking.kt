package br.com.pradofigu.maestro.domain.order.model

import br.com.pradofigu.maestro.domain.product.model.ProductPreparation
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

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

enum class OrderStatus {
    RECEIVED,
    IN_PREPARATION,
    READY,
    FINISHED
}