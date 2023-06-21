package br.com.pradofigu.maestro.resources.products

import br.com.pradofigu.maestro.domain.products.Product.UpdateProduct
import br.com.pradofigu.maestro.domain.products.Product.CreateProduct
import java.math.BigDecimal
data class ProductRequest(
    val name: String,
    val price: BigDecimal,
    val category: String,
    val preparationTime: BigDecimal
) {
    fun toCreateProduct() : CreateProduct {
        return CreateProduct(
            name = this.name,
            price = this.price,
            category = this.category,
            preparationTime = this.preparationTime
        )
    }

    fun toUpdateProduct() : UpdateProduct {
        return UpdateProduct(
            name = this.name,
            price = this.price,
            category = this.category,
            preparationTime = this.preparationTime
        )
    }

}