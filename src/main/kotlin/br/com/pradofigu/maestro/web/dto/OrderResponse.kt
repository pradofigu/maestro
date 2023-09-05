package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import java.util.UUID

data class OrderResponse(
    val id: UUID,
    val number: Long,
    val customerId: String?,
    val products: List<ProductResponse> = emptyList(),
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id!!,
            number = order.number!!,
            customerId = order.customer!!.id.toString(),
            products = order.products.map { ProductResponse.from(it) },
            paymentStatus = order.paymentStatus
        )
    }
}
