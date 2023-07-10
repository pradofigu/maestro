package br.com.pradofigu.maestro.domain.order.model

import java.util.UUID

data class PendingPaymentOrder(
    val id: UUID,
    val number: Long,
)
