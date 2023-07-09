package br.com.pradofigu.maestro.input.restapi.product.dto

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryResponse
import java.math.BigDecimal
import java.util.UUID

data class ProductResponse(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val category: CategoryResponse,
    val preparationTime: BigDecimal
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = product.id!!.toString(),
            name = product.name,
            price = product.price,
            category = product.category.let { CategoryResponse.from(it) },
            preparationTime = product.preparationTime
        )
    }
}
