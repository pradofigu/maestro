package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.CreateOrder
import java.util.UUID

data class CreateOrderRequest(
    val customerId: UUID?,
    val productsId: List<UUID>
) {
    fun toModel() = CreateOrder(
        customerId = customerId,
        productsId = productsId
    )
}