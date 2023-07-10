package br.com.pradofigu.maestro.input.restapi.order.dto

import br.com.pradofigu.maestro.domain.order.model.PendingPaymentOrder
import java.util.UUID

data class CreateOrderResponse(val id: UUID, val number: Long) {

    companion object {

        fun from(domain: PendingPaymentOrder) = CreateOrderResponse(domain.id, domain.number)

    }

}


