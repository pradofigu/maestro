package br.com.pradofigu.maestro.domain.customer.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Product(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
