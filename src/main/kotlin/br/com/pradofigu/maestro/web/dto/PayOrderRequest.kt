package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.OrderPayment
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
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