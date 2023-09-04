package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import java.util.UUID

data class OrderResponse(
    val id: UUID,
    val number: Long,
    val customerId: String?,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id!!,
            customerId = order.customer!!.id.toString(),
            number = order.number!!,
            paymentStatus = order.paymentStatus
        )
    }
}
