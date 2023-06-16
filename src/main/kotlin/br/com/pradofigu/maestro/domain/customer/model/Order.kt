package br.com.pradofigu.maestro.domain.customer.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Order(
        val id: UUID,
        val orderNumber: Number,
        val products: List<Product>,
        val totalPrice: BigDecimal,
        val statusOrder: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
)