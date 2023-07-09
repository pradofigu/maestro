package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.product.model.Product

data class OrderResponse(
    val id: String,
    val number: Long,
    val customerId: String?,
    val products: List<Product>,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id!!.toString(),
            customerId = order.customerId.toString(),
            number = order.number,
            products = order.products,
            paymentStatus = order.paymentStatus
        )
    }

}
