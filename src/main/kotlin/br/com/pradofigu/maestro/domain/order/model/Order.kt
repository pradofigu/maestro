package br.com.pradofigu.maestro.domain.order.model

import br.com.pradofigu.maestro.domain.customer.model.Customer
import java.util.UUID

data class Order(
    val id: UUID? = UUID.randomUUID(),
    val number: Long,
    val customer: Customer?,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING
)
enum class PaymentStatus { PENDING, PAID, REJECT }
