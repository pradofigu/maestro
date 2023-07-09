package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import java.util.UUID

data class OrderRequest(
    val number: Long,
    val customerId: UUID?,
    val paymentStatus: PaymentStatus
) {
    fun toModel() = Order(
        number = number,
        customerId = customerId,
        paymentStatus = paymentStatus
    )
}