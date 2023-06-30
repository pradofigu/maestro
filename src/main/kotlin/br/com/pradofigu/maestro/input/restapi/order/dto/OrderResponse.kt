package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import java.util.UUID

data class OrderResponse(
    val id: String,
    val number: Long,
    val customer: Customer,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id!!.toString(),
            customer = order.customer,
            number = order.number,
            paymentStatus = order.paymentStatus
        )
    }

}
