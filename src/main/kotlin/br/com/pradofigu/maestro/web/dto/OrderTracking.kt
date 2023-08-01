package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.OrderStatus
import br.com.pradofigu.maestro.usecase.model.OrderTracking
import java.math.BigDecimal

data class OrderTrackingRequest(val status: OrderStatus)

data class TrackingDetailsResponse(
    val orderNumber: Long,
    val status: OrderStatus,
    val remainingTimeInMinutes: BigDecimal
) {
    companion object {
        fun from(orderTracking: OrderTracking) = TrackingDetailsResponse(
            orderNumber = orderTracking.orderNumber!!,
            status = orderTracking.status,
            remainingTimeInMinutes = orderTracking.calculatePreparationTime()
        )
    }
}