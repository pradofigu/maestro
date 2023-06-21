package br.com.pradofigu.maestro.resources.products

import br.com.pradofigu.maestro.domain.products.Product
import java.math.BigDecimal

data class ProductResponse(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val category: String,
    val preparationTime: BigDecimal
) {
    companion object {
        fun from(product: Product) : ProductResponse {
            return ProductResponse(
                id= product.id.toString(),
                name = product.name,
                price = product.price,
                category = product.category,
                preparationTime = product.preparationTime
            )
        }
    }

}
