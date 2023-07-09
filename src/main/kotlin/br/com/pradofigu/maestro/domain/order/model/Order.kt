package br.com.pradofigu.maestro.domain.order.model

import java.util.UUID

data class Order(
    val id: UUID? = UUID.randomUUID(),
    val number: Long,
    val customerId: UUID?,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING
)
enum class PaymentStatus { PENDING, PAID, REJECT }
