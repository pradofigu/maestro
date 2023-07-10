package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.OrderStatus
import br.com.pradofigu.maestro.domain.order.model.OrderTracking

data class TrackingResponse(
    val orderNumber: Long,
    val status: OrderStatus
) {
    companion object {
        fun from(orderTracking: OrderTracking) = TrackingResponse(
            orderNumber = orderTracking.orderNumber!!,
            status = orderTracking.status
        )
    }
}