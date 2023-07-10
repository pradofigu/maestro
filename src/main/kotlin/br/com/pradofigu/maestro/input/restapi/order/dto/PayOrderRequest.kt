package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.OrderPayment
import br.com.pradofigu.maestro.domain.order.model.PaymentStatus
import java.util.*

data class PayOrderRequest(
    val number: Long,
    val status: PaymentStatus,
) {
    fun toModel(id: String) = OrderPayment(
        id = UUID.fromString(id),
        number = number,
        status = status
    )
}