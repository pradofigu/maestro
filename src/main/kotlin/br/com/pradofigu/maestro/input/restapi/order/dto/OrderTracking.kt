package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.OrderStatus
import br.com.pradofigu.maestro.domain.order.model.OrderTracking
import java.math.BigDecimal

data class OrderTrackingRequest(val status: OrderStatus)

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

data class TrackingDetailsResponse(
    val orderNumber: Long,
    val status: OrderStatus,
    val amount: BigDecimal
) {
    companion object {
        fun from(orderTracking: OrderTracking) = TrackingDetailsResponse(
            orderNumber = orderTracking.orderNumber!!,
            status = orderTracking.status,
            amount = orderTracking.calculatePreparationTime()
        )
    }
}