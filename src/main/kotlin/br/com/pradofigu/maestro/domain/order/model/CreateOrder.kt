package br.com.pradofigu.maestro.domain.order.model

import br.com.pradofigu.maestro.domain.order.model.PaymentStatus.PENDING
import java.util.UUID

data class CreateOrder(
    val customerId: UUID?,
    val productsId: List<UUID>
) {
    val paymentStatus: PaymentStatus = PENDING
}
