package br.com.pradofigu.maestro.domain.order.model

import java.util.UUID

data class Order(
    val id: UUID?,
    val number: Long,
    val customerId: UUID?,
    val paymentStatus: PaymentStatus
)

enum class PaymentStatus { PENDING, PAID, REJECT }

