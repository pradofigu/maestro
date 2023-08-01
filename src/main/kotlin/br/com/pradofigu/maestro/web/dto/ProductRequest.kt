package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.domain.product.model.Product
import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val price: BigDecimal,
    val category: CategoryRequest,
    val description: String,
    val imageUrl: String?,
    val preparationTime: BigDecimal
) {
    fun toModel() = Product(
        name = name,
        price = price,
        category = category.toModel(),
        description = description,
        imageUrl = imageUrl,
        preparationTime = preparationTime
    )
}