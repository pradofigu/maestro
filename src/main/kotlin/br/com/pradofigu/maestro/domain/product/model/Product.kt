package br.com.pradofigu.maestro.domain.product.model

import br.com.pradofigu.maestro.domain.category.model.Category
import java.util.UUID
import java.math.BigDecimal

data class Product(
    val id: UUID? = UUID.randomUUID(),
    val name: String,
    val description: String,
    val imageUrl: String? = null,
    val price: BigDecimal,
    val category: Category,
    val preparationTime: BigDecimal
)

data class ProductPreparation(
    val id: UUID? = UUID.randomUUID(),
    val preparationTime: BigDecimal
)
