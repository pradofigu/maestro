package br.com.pradofigu.maestro.domain.product.model

import java.util.UUID
import java.math.BigDecimal

data class Product(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val price: BigDecimal,
    val category: String,
    val preparationTime: BigDecimal
)
