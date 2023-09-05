package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.OrderStatus
import br.com.pradofigu.maestro.usecase.model.OrderTracking

data class OrderTrackingRequest(val status: OrderStatus)

data class OrderTrackingResponse(
    val id: String,
    val order: OrderResponse,
    val status: OrderStatus
) {
    companion object {
        fun from(orderTracking: OrderTracking) = OrderTrackingResponse(
            id = orderTracking.id.toString(),
            order = OrderResponse.from(orderTracking.order),
            status = orderTracking.status
        )
    }
}

data class TrackingDetailsResponse(
    val orderNumber: Long,
    val status: OrderStatus,
    val remainingTimeInMinutes: Int
) {
    companion object {
        fun from(orderTracking: OrderTracking) = TrackingDetailsResponse(
            orderNumber = orderTracking.order.number!!,
            status = orderTracking.status,
            remainingTimeInMinutes = orderTracking.products.sumOf { it.preparationTime }
        )
    }
}