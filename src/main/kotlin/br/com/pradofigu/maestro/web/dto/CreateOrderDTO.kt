package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.CreateOrder
import java.util.UUID

data class CreateOrderRequest(
    val customerId: UUID?,
    val productsId: List<UUID>
) {
    fun toModel() = CreateOrder(customerId, productsId)
}

data class CreateOrderResponse(
    val id: UUID,
    val number: Long,
    val products: List<ProductResponse>
)