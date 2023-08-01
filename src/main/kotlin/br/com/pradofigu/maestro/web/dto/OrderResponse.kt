package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus

data class OrderResponse(
    val id: String,
    val number: Long,
    val customerId: String?,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id!!.toString(),
            customerId = order.customerId.toString(),
            number = order.number,
            paymentStatus = order.paymentStatus
        )
    }
}
