package br.com.pradofigu.maestro.domain.orders

import br.com.pradofigu.maestro.domain.customers.Customer
import br.com.pradofigu.maestro.domain.products.Product
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Order(
        val id: UUID,
        val number: Long,
        val customer: Customer,
        val products: List<Product>,
        val paymentStatus: PaymentStatus
) {
        class CreateOrder(
                val customerId: UUID,
                val products: List<Product>,
                val paymentStatus: PaymentStatus = PaymentStatus.PENDING
        )

        class UpdatePaymentStatus(
                val paymentStatus: PaymentStatus
        )
}
enum class PaymentStatus { PENDING, PAID, REJECT }
