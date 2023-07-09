package br.com.pradofigu.maestro.input.restapi.product.dto

import br.com.pradofigu.maestro.domain.product.model.Product
import br.com.pradofigu.maestro.input.restapi.category.dto.CategoryRequest
import java.math.BigDecimal
import java.math.BigInteger

data class ProductRequest(
    val name: String,
    val price: BigDecimal,
    val category: CategoryRequest,
    val preparationTime: BigInteger
) {
    fun toModel() = Product(
        name = name,
        price = price,
        category = category.toModel(),
        preparationTime = preparationTime
    )
}