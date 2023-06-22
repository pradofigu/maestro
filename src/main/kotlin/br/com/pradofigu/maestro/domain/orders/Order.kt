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
        val status: OrderStatus,
        val paymentStatus: PaymentStatus
) {
        class CreateOrder(
                val customerId: UUID,
                val products: List<Product>,
                val status: OrderStatus = OrderStatus.PENDING,
                val paymentStatus: PaymentStatus = PaymentStatus.PENDING
        )

        class UpdateOrder(
                val status: OrderStatus
        )
}
enum class OrderStatus { PENDING, IN_PREPARATION, READY }
enum class PaymentStatus { PENDING, PAID, REJECT }
