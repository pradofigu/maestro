package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.customer.model.Customer
import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.input.restapi.customer.dto.CustomerResponse
import java.util.UUID

data class OrderResponse(
    val id: String,
    val number: Long,
    val customer: CustomerResponse?,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id!!.toString(),
            customer = order.customer?.let { CustomerResponse.from(it) },
            number = order.number,
            paymentStatus = order.paymentStatus
        )
    }

}
