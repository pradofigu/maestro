package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.Order
import br.com.pradofigu.maestro.usecase.model.PaymentStatus
import java.util.UUID

data class PayOrderRequest(
    val number: Long,
    val status: PaymentStatus,
) {

    fun toModel(id: UUID) = Order(
        id = id,
        number = number,
        paymentStatus = status
    )
}