package br.com.pradofigu.maestro.domain.product.model

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID
import java.math.BigDecimal

data class Product(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val price: BigDecimal,
    val category: Category,
    val preparationTime: BigDecimal
)
