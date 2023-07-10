package br.com.pradofigu.maestro.domain.order.model

import java.time.LocalDateTime
import java.util.UUID

data class OrderTracking(
    val id: UUID,
    val orderId: UUID,
    val status: OrderStatus,
    val createdAt: LocalDateTime
) {
    var orderNumber: Long? = null
}

enum class OrderStatus {
    RECEIVED,
    IN_PREPARATION,
    READY,
    FINISHED
}