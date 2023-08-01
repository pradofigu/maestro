package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.domain.order.model.CreateOrder
import br.com.pradofigu.maestro.domain.order.model.PendingPaymentOrder
import java.util.UUID

data class CreateOrderRequest(
    val customerId: UUID?,
    val productsId: List<UUID>
) {
    fun toModel() = CreateOrder(customerId, productsId)
}

data class CreateOrderResponse(val id: UUID, val number: Long) {

    companion object {
        fun from(domain: PendingPaymentOrder) = CreateOrderResponse(domain.id, domain.number)
    }
}