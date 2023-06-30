package br.com.pradofigu.maestro.input.restapi.product.dto

import br.com.pradofigu.maestro.domain.product.model.Product
import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val price: BigDecimal,
    val category: String,
    val preparationTime: BigDecimal
) {
    fun toModel() = Product(
        name = name,
        price = price,
        category = category,
        preparationTime = preparationTime
    )
}