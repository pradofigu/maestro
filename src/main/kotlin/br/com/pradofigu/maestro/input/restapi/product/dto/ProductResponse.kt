package br.com.pradofigu.maestro.input.restapi.product.dto

import br.com.pradofigu.maestro.domain.product.model.Product
import java.math.BigDecimal
import java.util.UUID

data class ProductResponse(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val preparationTime: BigDecimal
) {
    companion object {
        fun from(product: Product) = ProductResponse(
            id = product.id!!.toString(),
            name = product.name,
            price = product.price,
            category = product.category,
            preparationTime = product.preparationTime
        )
    }
}
