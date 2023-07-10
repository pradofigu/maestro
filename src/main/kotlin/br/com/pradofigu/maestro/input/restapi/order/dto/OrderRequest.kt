package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.Order
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import br.com.pradofigu.maestro.domain.product.model.Product
import java.util.UUID

data class OrderRequest(
    val id: UUID?,
    val number: Long,
    val customerId: UUID?,
    val products: List<Product>,
    val paymentStatus: PaymentStatus
) {
    fun toModel() = Order(
        id = id,
        number = number,
        customerId = customerId,
        paymentStatus = paymentStatus
    )
}