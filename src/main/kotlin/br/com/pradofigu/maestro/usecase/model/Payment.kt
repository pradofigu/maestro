package br.com.pradofigu.maestro.usecase.model

import java.util.UUID

enum class PaymentStatus { PENDING, PAID, REJECT }

data class PendingPaymentOrder(
    val id: UUID,
    val number: Long,
)
