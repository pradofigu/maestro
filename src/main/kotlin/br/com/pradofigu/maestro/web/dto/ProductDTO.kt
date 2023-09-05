package br.com.pradofigu.maestro.web.dto

import br.com.pradofigu.maestro.usecase.model.Product
import java.math.BigDecimal
import java.util.UUID

data class ProductRequest(
    val name: String,
    val price: BigDecimal,
    val category: CategoryRequest,
    val description: String,
    val imageUrl: String?,
    val preparationTime: Int
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

data class ProductResponse(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val category: CategoryResponse,
    val description: String,
    val imageUrl: String?,
    val preparationTime: Int
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = product.id!!,
            name = product.name,
            price = product.price,
            category = product.category.let { CategoryResponse.from(it) },
            description = product.description,
            imageUrl = product.imageUrl,
            preparationTime = product.preparationTime
        )
    }
}