package br.com.pradofigu.maestro.domain.orders

import br.com.pradofigu.maestro.domain.customers.Customer
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Order(
        val id: UUID,
        val orderNumber: Long,
        val customer: Customer,
        val products: List<Product>,
        val totalPrice: BigDecimal,
        val statusOrder: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
) {
        class CreateOrder(
                val customerId: UUID,
                val products: List<Product>,
                val totalPrice: BigDecimal,
        )

        class UpdateStatus(
                val id: UUID,
                val statusOrder: String,
        )
}

