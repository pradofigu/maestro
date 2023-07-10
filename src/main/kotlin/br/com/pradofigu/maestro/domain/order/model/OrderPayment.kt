package br.com.pradofigu.maestro.domain.order.model

import java.util.UUID

data class OrderPayment(
    val id: UUID,
    val number: Long,
    val status: PaymentStatus
)
